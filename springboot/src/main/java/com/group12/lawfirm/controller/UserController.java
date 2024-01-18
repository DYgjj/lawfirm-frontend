package com.group12.lawfirm.controller;

import javax.validation.Valid;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.group12.lawfirm.common.MD5Util;
import com.group12.lawfirm.common.RegexUtils;
import com.group12.lawfirm.common.Result;
import com.group12.lawfirm.entity.Params;
import com.group12.lawfirm.entity.User;
import com.group12.lawfirm.exception.CustomException;
import com.group12.lawfirm.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        user.setPassword(MD5Util.MD5(user.getPassword()));
        User loginUser = userService.login(user);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        user.setPassword(MD5Util.MD5(user.getPassword()));

        userService.add(user);
        return Result.success();
    }

    @GetMapping
    public Result getUser(){
        List<User> list = userService.getUser();
        return Result.success(list);
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<User> info = userService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        user.setPassword(MD5Util.MD5(user.getPassword()));
        if (user.getId() == null) {
            userService.add(user);
        } else {
            userService.update(user);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<User> list) {

        for (User user : list) {
            userService.delete(user.getId());
        }
        return Result.success();
    }

    @PutMapping("/changePassword")
    public Result profile(@RequestBody User user) {
        userService.changePassword(user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result password(@RequestBody User user) {
        userService.resetPassword(user);
        return Result.success();
    }
}
