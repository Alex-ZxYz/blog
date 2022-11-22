package com.ZxYz.controller;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.User;
import com.ZxYz.enums.AppHttpCodeEnum;
import com.ZxYz.exception.SystemException;
import com.ZxYz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController

public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //必须传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }



    @PostMapping("/logout")
    public ResponseResult<?> logout(){
        return loginService.logout();
    }
}
