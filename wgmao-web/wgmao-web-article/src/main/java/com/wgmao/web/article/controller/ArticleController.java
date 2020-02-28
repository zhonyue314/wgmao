package com.wgmao.web.article.controller;

import com.wgmao.article.feign.ArticleFeign;
import com.wgmao.article.pojo.Article;
import com.wgmao.article.pojo.ArticleUser;
import com.wgmao.entity.Result;
import com.wgmao.user.feign.UserFeign;
import com.wgmao.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/2/14 10:27
 */
@Controller
@RequestMapping("/warticle")
public class ArticleController {
    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private UserFeign userFeign;

    //查询
    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articleList = articleFeign.findAll();
        List articleUserList = new ArrayList<>();
        Map map = new HashMap();
        for (int i = 0; i < articleList.size(); i++) {
            ArticleUser articleUser = new ArticleUser();
            Article article = articleList.get(i);
            String userid = article.getUserid();
            User user = userFeign.findOne(userid);
            String username = user.getUsername();
            articleUser.setArticle(article);
            articleUser.setUsername(username);
            articleUserList.add(articleUser);
        }
        map.put("articleUserList", articleUserList);
        model.addAttribute("items", map);
        return "index";
    }

    @GetMapping("/content")
    public String content(String id, Model model) {
        //查询文章内容和文章作者名字
        Result<Article> result = articleFeign.findById(id);
        Article article = result.getData();
        User user1 = userFeign.findOne(article.getUserid());
        String username1 = user1.getUsername();
        ArticleUser articleUserVo = new ArticleUser();
        articleUserVo.setArticle(article);
        articleUserVo.setUsername(username1);
//        map.put("articleUserVo", articleUserVo);
        model.addAttribute("articleUserVo",articleUserVo);
        //查询推荐文章的标题和文章作者
        List<Article> articleList = articleFeign.findAll();
        List articleUserList = new ArrayList<>();
        for (int i = 0; i < articleList.size(); i++) {
            ArticleUser articleUser = new ArticleUser();
            Article article1 = articleList.get(i);
            String userid = article1.getUserid();
            User user = userFeign.findOne(userid);
            String username = user.getUsername();
            articleUser.setArticle(article1);
            articleUser.setUsername(username);
            articleUserList.add(articleUser);
        }
        model.addAttribute("articleUserList", articleUserList);
        return "article-content";
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat,true));
//    }
}