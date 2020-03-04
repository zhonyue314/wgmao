package com.wgmao.article.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.article.dao.ArticleMapper;
import com.wgmao.article.pojo.Article;
import com.wgmao.article.pojo.ArticleUser;
import com.wgmao.article.service.ArticleService;
import com.wgmao.entity.Result;
import com.wgmao.notice.feign.NoticeFeign;
import com.wgmao.notice.pojo.Notice;
import com.wgmao.user.feign.UserFeign;
import com.wgmao.user.pojo.User;
import com.wgmao.util.IdWorker;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private NoticeFeign noticeFeign;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserFeign userFeign;

    /**
     * 订阅
     *
     * @param articleId
     * @param userId
     * @return
     */
    @Override
    public Boolean subscribe(String articleId, String userId) {
        //根据文章id，查询作者id
        String authorId = articleMapper.selectById(articleId).getUserid();
        //创建rabbit
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        //声明交换机类型为Direct
        DirectExchange exchange = new DirectExchange("article_subscribe");
        rabbitAdmin.declareExchange(exchange);
        //创建队列
        Queue queue = new Queue("article_subscribe_" + userId, true);
        //声明绑定关系，只接收对应作者的消息
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(authorId);

        //查询订阅关系
        //存放用户订阅的集合key,存储作者id
        String userKey = "article_subscribe_" + userId;
        //存放作者订阅消息的集合key，存储订阅者id
        String authorKey = "article_author_" + authorId;

        Boolean flag = redisTemplate.boundSetOps(userKey).isMember(authorId);
        if (flag == true) {
            //取消订阅
            redisTemplate.boundSetOps(userKey).remove(authorId);
            redisTemplate.boundSetOps(authorKey).remove(userId);
            //解除绑定
            rabbitAdmin.removeBinding(binding);
            return false;
        } else {
            //订阅
            redisTemplate.boundSetOps(userKey).add(authorId);
            redisTemplate.boundSetOps(authorKey).add(userId);

            //添加绑定
            rabbitAdmin.declareBinding(binding);
            return true;
        }
    }

    @Override
    public Map selectArticleRecommend(String articleId) {
        Map map = new HashMap<>();
        //封装文章
        Article article = articleMapper.selectById(articleId);
        map.put("article", article);

        //封装相关推荐文章
        ArrayList<ArticleUser> articleUserList = new ArrayList<>();
        List<Article> articleList = articleMapper.selectList(null);
        for (Article article1 : articleList) {
            ArticleUser articleUser = new ArticleUser();
            User user = null;
            try {
                user = userFeign.findOne(article1.getUserid());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String username = user.getUsername();
            articleUser.setArticle(article);
            articleUser.setUsername(username);
            articleUserList.add(articleUser);
        }
        map.put("articleUserList", articleUserList);
        return map;
    }

    /**
     * 文章点赞
     *
     * @param articleId
     * @param userId
     */
    @Override
    public void thumbup(String articleId, String userId) {
        //添加点赞数
        Article article = articleMapper.selectById(articleId);
        article.setThumbup(article.getThumbup() + 1);
        articleMapper.updateById(article);

        //点赞成功，发送消息通知作者
        Notice notice = new Notice();
        //接收消息用户的id
        notice.setReceiverId(article.getUserid());
        //进行操作的id
        notice.setOperatorId(userId);
        //操作类型
        notice.setAction("publish");
        //被操作对象
        notice.setTargetType("article");
        //被操作对象id
        notice.setTargetId(articleId);
        //通知类型
        notice.setType("user");

        //保存消息
        noticeFeign.save(notice);

        //创建rabbit管理器
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        //创建队列，作者为队列，作者id区分
        Queue queue = new Queue("article_thumbup_" + article.getUserid(), true);
        rabbitAdmin.declareQueue(queue);
        //发送消息
        rabbitTemplate.convertAndSend("article_thumbup_" + article.getUserid(), userId);
    }


    @Override
    public List<Article> findAll() {
        List<Article> articleList = articleMapper.selectList(null);
        return articleList;
    }

    @Override
    public Article findById(String articleId) {
        Article article = articleMapper.selectById(articleId);
        return article;
    }

    @Override
    public void save(Article article) {
        String id = idWorker.nextId() + "";
        article.setId(id);
        //初始化数据
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);
        articleMapper.insert(article);
    }

    @Override
    public void updateById(Article article) {
        articleMapper.updateById(article);
    }

    @Override
    public void deleteById(String articleId) {
        articleMapper.deleteById(articleId);
    }

    public Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {
        //设置条件查询
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            wrapper.eq(map.get(key) != null, key, map.get(key));
        }
        Page<Article> pageData = new Page<>(page, size);
        List<Article> list = articleMapper.selectPage(pageData, wrapper);
        pageData.setRecords(list);
        return pageData;
    }

    @Override
    public List<Article> list() {
        List<Article> list = articleMapper.selectList(null);
        return list;
    }
}
