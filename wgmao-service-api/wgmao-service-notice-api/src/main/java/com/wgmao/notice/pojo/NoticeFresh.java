package com.wgmao.notice.pojo;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 18:38
 */
@TableName("tb_notice_fresh")
public class NoticeFresh implements Serializable {
    private String userId;
    private String noticeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}
