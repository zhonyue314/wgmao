package com.wgmao.notice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.article.feign.ArticleFeign;
import com.wgmao.entity.Result;
import com.wgmao.notice.dao.NoticeFreshMapper;
import com.wgmao.notice.dao.NoticeMapper;
import com.wgmao.notice.pojo.Notice;
import com.wgmao.notice.pojo.NoticeFresh;
import com.wgmao.notice.service.NoticeService;
import com.wgmao.user.feign.UserFeign;
import com.wgmao.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 18:04
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private NoticeFreshMapper noticeFreshMapper;

    /**
     * 存储点赞消息
     *
     * @param notice
     */
    @Override
    public void save(Notice notice) {
        //初始化设置
        //设置读取状态
        notice.setState("0");
        notice.setCreatetime(new Date());
        //生成id
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeMapper.insert(notice);
    }

    @Override
    public Notice selectById(String id) {
        Notice notice = noticeMapper.selectById(id);
        return notice;
    }

    @Override
    public void updateById(Notice notice) {
        noticeMapper.updateById(notice);
    }


    @Override
    public void freshDelete(NoticeFresh noticeFresh) {
        noticeFreshMapper.delete(new EntityWrapper<>(noticeFresh));
    }

    @Override
    public Page<NoticeFresh> freshPage(String userId, Integer page, Integer size) {
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setUserId(userId);

        Page<NoticeFresh> pageData = new Page<>(page, size);
        List<NoticeFresh> noticeFreshList = noticeFreshMapper.selectPage(pageData, new EntityWrapper<>(noticeFresh));
        pageData.setRecords(noticeFreshList);
        return pageData;
    }

    @Override
    public Page<Notice> selectByPage(Notice notice, Integer page, Integer size) {
        //封装查询条件
        Page<Notice> pageData = new Page<>(page, size);
        List<Notice> noticeList = noticeMapper.selectPage(pageData, new EntityWrapper<>(notice));

        for (Notice n : noticeList) {
            getInfo(n);
        }
        //封装结果到分页对象中
        pageData.setRecords(noticeList);
        return pageData;
    }


    //完善消息内容
    private void getInfo(Notice notice) {
        //查询用户昵称
        Result userResult = userFeign.selectById(notice.getOperatorId());
        HashMap userMap = (HashMap) userResult.getData();
        //设置操作者用户昵称到消息通知中
        notice.setOperatorName(userMap.get("nickname").toString());

        //查询对象名称
        Result articleResult = articleFeign.findById(notice.getTargetId());
        HashMap articleMap = (HashMap) articleResult.getData();
        //设置对象名称到消息通知中
        notice.setTargetName(articleMap.get("title").toString());
    }
}
