package com.wgmao.user;

import com.wgmao.user.dao.UserMapper;
import com.wgmao.user.pojo.User;
import com.wgmao.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/2/16 10:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserTest {
    @Autowired
    public UserService userService;

    @Test
    public void test2(){
        User user = userService.findOne("1");
        System.out.println(user);
    }
}
