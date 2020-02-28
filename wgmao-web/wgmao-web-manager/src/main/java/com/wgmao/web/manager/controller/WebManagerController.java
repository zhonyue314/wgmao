package com.wgmao.web.manager.controller;

import com.wgmao.article.feign.ArticleFeign;
import com.wgmao.article.pojo.Article;
import com.wgmao.entity.Result;
import com.wgmao.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@Controller
@RequestMapping("/manager")
public class WebManagerController {
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ArticleFeign articleFeign;


}
