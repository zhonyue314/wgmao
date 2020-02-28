package com.wgmao.web.article;

import com.wgmao.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/2/14 10:08
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.wgmao.article.feign","com.wgmao.user.feign"})
public class WebArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebArticleApplication.class,args);
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}
