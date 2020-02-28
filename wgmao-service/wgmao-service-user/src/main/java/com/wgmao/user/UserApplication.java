package com.wgmao.user;

import com.wgmao.user.config.TokenDecode;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/1/10 19:52
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.wgmao.user.dao")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }
}


