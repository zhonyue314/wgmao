package com.wgmao.user.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2019/12/24 18:41
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 交换机
     */
    //添加积分
    public static final String EX_BUYING_ADDPOINTUSER = "ex_buying_addpointuser";

    /**
     * 队列
     */
    //添加积分
    public static final String CG_BUYING_ADDPOINT = "cg_buying_addpoint";
    //完成添加积分
    public static final String CG_BUYING_FINISHADDPOIN = "cg_buying_finishaddpoin";

    /**
     * 路由
     */
    //添加积分路由key
    public static final String CG_BUYING_ADDPOIN_KEY = "cg_buying_addpoin_key";
    //完成添加积分路由key
    public static final String CG_BUYING_FINISHADDPOINT_KEY = "cg_buying_finishaddpoint_key";

    //声明交换机
    @Bean
    public Exchange exBuyingAddPointUser() {
        return ExchangeBuilder.directExchange(EX_BUYING_ADDPOINTUSER).durable(true).build();
    }

    //声明队列
    @Bean
    public Queue queueCgBuyingAddPoint() {
        return new Queue(CG_BUYING_ADDPOINT);
    }

    @Bean
    public Queue queueCgBuyingFinishAddPoint() {
        return new Queue(CG_BUYING_FINISHADDPOIN);
    }

    //队列绑定交换机
    @Bean
    public Binding bindingCgBuyingAddPoint(@Qualifier("queueCgBuyingAddPoint") Queue queue, @Qualifier("exBuyingAddPointUser") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CG_BUYING_ADDPOIN_KEY).noargs();
    }

    @Bean
    public Binding bindingCgBuyingFinishAddPoint(@Qualifier("queueCgBuyingFinishAddPoint") Queue queue, @Qualifier("exBuyingAddPointUser") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CG_BUYING_FINISHADDPOINT_KEY).noargs();
    }
}
