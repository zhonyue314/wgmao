package com.wgmao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/1/10 21:02
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.wgmao.oauth.dao")
@EnableFeignClients(basePackages = {"com.wgmao.user.feign"})
public class OAuThApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuThApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
