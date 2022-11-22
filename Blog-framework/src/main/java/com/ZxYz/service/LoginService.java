package com.ZxYz.service;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;



public interface LoginService {

    ResponseResult<?> login(User user);

    ResponseResult<?> logout();
}
