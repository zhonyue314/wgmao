package com.wgmao.user.service.impl;

import com.wgmao.user.dao.UserMapper;
import com.wgmao.user.pojo.User;
import com.wgmao.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/1/10 19:55
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserMapper userMapper;

    public User selectById(String username){
        User user1 = new User();
        user1.setUsername(username);
        User user = userMapper.selectOne(user1);
        return user;
    }

    @Override
    public User findOne(String id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> list = userMapper.selectList(null);
        return list;
    }

    @Override
    public User login(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void delete(String username) {
        userMapper.deleteById(username);
    }
}
