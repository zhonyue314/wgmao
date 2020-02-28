package com.wgmao.user.service;

import com.wgmao.user.pojo.User;

import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/1/10 19:51
 */
public interface UserService {
    //查询所有
    List<User> findAll();
    //根基id查询
    User login(User user);
    //添加用户
    void add(User user);
    //修改
    void update(User user);
    //删除
    void delete(String username);

    User selectById(String username);

    User findOne(String id);
}
