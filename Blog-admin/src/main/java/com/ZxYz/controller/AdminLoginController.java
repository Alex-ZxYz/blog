package com.ZxYz.controller;


import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.User;
import com.ZxYz.enums.AppHttpCodeEnum;
import com.ZxYz.exception.SystemException;
import com.ZxYz.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminLoginController {

    @Autowired
    AdminLoginService adminLoginService;

    @PostMapping("/user/login")
    public ResponseResult<?> adminLogin(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //必须传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
      return adminLoginService.adminLogin(user);
    }
}
