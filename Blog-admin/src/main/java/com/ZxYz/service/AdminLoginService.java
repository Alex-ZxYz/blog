package com.ZxYz.service;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.User;

public interface AdminLoginService {

    ResponseResult<?> adminLogin(User user);
}
