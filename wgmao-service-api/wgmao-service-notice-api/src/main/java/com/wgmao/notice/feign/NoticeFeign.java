package com.wgmao.notice.feign;

import com.wgmao.entity.Result;
import com.wgmao.notice.pojo.Notice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@FeignClient(name = "notice")
public interface NoticeFeign {
    @PostMapping
    Result save(@RequestBody Notice notice);
}
