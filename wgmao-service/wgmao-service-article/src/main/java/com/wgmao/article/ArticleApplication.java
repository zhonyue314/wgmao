package com.wgmao.article;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.wgmao.interceptor.FeignInterceptor;
import com.wgmao.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: zyxyz zhongyue314@163.com
 * @date: 2020/1/11
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.wgmao.article.dao")
@EnableFeignClients(basePackages = {"com.wgmao.notice.feign"})
public class ArticleApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

    @Bean
    public PaginationInterceptor createPaginationInterceptor() {
        return new PaginationInterceptor();
    }
}
