package com.wgmao.notice.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.notice.pojo.Notice;
import com.wgmao.notice.pojo.NoticeFresh;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 18:04
 */
public interface NoticeService  {
    void save(Notice notice);

    Notice selectById(String id);

    void updateById(Notice notice);

    Page<NoticeFresh> freshPage(String userId, Integer page, Integer size);

    void freshDelete(NoticeFresh noticeFresh);

    Page<Notice> selectByPage(Notice notice, Integer page, Integer size);
}
