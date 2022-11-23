package com.ZxYz.service;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.User;
import com.ZxYz.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User>  {
    ResponseResult<?> userInfo();

    ResponseResult<?> updateUserInfo(User user);

    ResponseResult<?> register(User user);
}
