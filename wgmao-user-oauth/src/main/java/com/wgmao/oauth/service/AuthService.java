package com.wgmao.oauth.service;

import com.wgmao.oauth.util.AuthToken;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
public interface AuthService {
    AuthToken login(String username,String password,String clientId,String clientSecret);
}
