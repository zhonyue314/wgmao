package com.wgmao.article.feign;

import com.wgmao.article.pojo.Article;
import com.wgmao.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@FeignClient(name = "article")
public interface ArticleFeign {

    @GetMapping("/article/list")
    List<Article> findAll();

//    @GetMapping("/article")
//    List<Article> list();

    @GetMapping("/article/articleContent/{articleId}")
    Map selectArticleRecommend(@PathVariable("articleId") String articleId);

    @GetMapping("/article/page/{page}/{size}")
    Result findByPage(@RequestBody Map<String, Object> map, @PathVariable("page") Integer page, @PathVariable("size") Integer size);

    @GetMapping("/article/{articleId}")
    Article findById(@PathVariable("articleId") String articleId);

}
