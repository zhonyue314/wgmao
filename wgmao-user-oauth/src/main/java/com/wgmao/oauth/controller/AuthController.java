package com.wgmao.oauth.controller;

import com.wgmao.entity.Result;
import com.wgmao.entity.StatusCode;
import com.wgmao.oauth.service.AuthService;
import com.wgmao.oauth.util.AuthToken;
import com.wgmao.oauth.util.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@Controller
@RequestMapping("/oauth")
public class AuthController {

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Autowired
    public AuthService authService;

    @RequestMapping("/toLogin")
    public String toLogin(@RequestParam(value = "FROM",required = false,defaultValue = "")String from, Model model){
        model.addAttribute("from",from);
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password, HttpServletResponse response){
        //校验参数
        if(StringUtils.isEmpty(username)){
            throw new RuntimeException("请输入用户名");
        }
        if(StringUtils.isEmpty(password)){
            throw new RuntimeException("请输入密码");
        }
        //申请令牌
        AuthToken authToken = authService.login(username, password, clientId, clientSecret);
        //将jti存入cookie
        this.saveJtiToCookie(authToken.getJti(),response);

        return new Result(true, StatusCode.OK,"登陆成功",authToken.getJti());
    }

    private void saveJtiToCookie(String jti, HttpServletResponse response) {
        CookieUtil.addCookie(response,cookieDomain,"/","uid",jti,cookieMaxAge,false);
    }
}
