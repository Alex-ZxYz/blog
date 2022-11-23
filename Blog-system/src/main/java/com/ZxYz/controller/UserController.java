package com.ZxYz.controller;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.User;
import com.ZxYz.service.UserService;
import com.qiniu.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

@RestController
@RequestMapping ("/user")
@Api(tags = "用户接口",description = "用户信息相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "用户信息",notes = "获取用户信息")
    public ResponseResult<?> userInfo(){
        return userService.userInfo();
    }
    @PutMapping("/userInfo")
    @ApiOperation(value = "更新用户",notes = "更新用户个人信息")
    public ResponseResult<?> updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes = "注册")
    public ResponseResult<?> register(@RequestBody User user){
        return userService.register(user);
    }

}
