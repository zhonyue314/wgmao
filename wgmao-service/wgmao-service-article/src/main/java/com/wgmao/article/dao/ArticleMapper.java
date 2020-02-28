package com.wgmao.article.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wgmao.article.pojo.Article;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/2/26 20:06
 */
public interface ArticleMapper extends BaseMapper<Article> {/*
    @Select("select tb_article.*,tb_user.username from wgmao_article.tb_article left join wgmao_user.tb_user on tb_user.id = tb_article.userid")
    List<ArticleUserVO> findAllArticleUser();*/
}
