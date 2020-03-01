package com.wgmao.user.feign;

import com.wgmao.entity.Result;
import com.wgmao.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@FeignClient(name = "user")
public interface UserFeign {
    @GetMapping("/user/load/{username}")
    User findUserInfo(@PathVariable("username") String username);

    @GetMapping("/user/{userId}")
    public Result selectById(@PathVariable("userId") String userId);

    @GetMapping("/user/findOne/{id}")
    User findOne(@PathVariable("id") String id);

}
