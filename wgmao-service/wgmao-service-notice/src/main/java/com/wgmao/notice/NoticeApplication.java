package com.wgmao.notice;

import com.wgmao.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 17:29
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.wgmao.notice.dao")
@EnableFeignClients(basePackages = {"com.wgmao.user.feign","com.wgmao.article.feign"})
public class NoticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
