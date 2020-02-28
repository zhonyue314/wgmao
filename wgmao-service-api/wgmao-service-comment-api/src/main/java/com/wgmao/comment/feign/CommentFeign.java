package com.wgmao.comment.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@FeignClient(name = "comment")
public interface CommentFeign {
}
