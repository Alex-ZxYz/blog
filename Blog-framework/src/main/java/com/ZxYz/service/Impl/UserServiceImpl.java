package com.ZxYz.service.Impl;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.User;
import com.ZxYz.domain.vo.UserInfoVo;
import com.ZxYz.mapper.UserMapper;
import com.ZxYz.service.UserService;
import com.ZxYz.utils.BeanCopyUtils;
import com.ZxYz.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public ResponseResult<?> userInfo() {
        //获取用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询个人信息
        User user = getById(userId);
        //封装UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }
}
