package com.wgmao.article;

import com.wgmao.article.dao.ArticleMapper;
import com.wgmao.article.pojo.Article;
import com.wgmao.article.pojo.ArticleUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/2/16 11:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApplication.class)
public class ArticleTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test1() {

    }
}
