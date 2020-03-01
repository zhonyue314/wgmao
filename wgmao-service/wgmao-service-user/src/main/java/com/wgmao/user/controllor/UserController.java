package com.wgmao.user.controllor;

import com.wgmao.entity.Result;
import com.wgmao.entity.StatusCode;
import com.wgmao.user.pojo.User;
import com.wgmao.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/1/10 20:24
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    public UserService userService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result findAll() {
        List<User> list = userService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/load/{username}")
    public User findUserInfo(@PathVariable("username") String username) {
        User user = userService.selectUser(username);
        return user;
    }

    @GetMapping("/findOne/{id}")
    public User findOne(@PathVariable("id") String id) {
        User user = userService.findOne(id);
        return user;
    }

    /**
     * 根据id查询数据
     *
     * @param userId
     * @return
     */
    @GetMapping("{userId}")
    public Result selectById(@PathVariable("userId") String userId) {
        return new Result(true, StatusCode.OK, "查询成功", userService.selectById(userId));
    }

    /**
     * 添加数据
     *
     * @param user
     * @return
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 修改数据
     *
     * @param user
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody User user) {
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除数据
     *
     * @param username
     * @return
     */
    @DeleteMapping(value = "{username}")
    public Result delete(@PathVariable("username") String username) {
        userService.delete(username);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
