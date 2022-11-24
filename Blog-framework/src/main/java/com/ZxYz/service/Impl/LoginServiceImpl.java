package com.ZxYz.service.Impl;

import com.ZxYz.utils.JwtUtil;
import com.ZxYz.utils.RedisCache;
import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.LoginUser;
import com.ZxYz.domain.entity.User;
import com.ZxYz.domain.vo.UserInfoVo;
import com.ZxYz.domain.vo.UserLoginVo;
import com.ZxYz.service.LoginService;
import com.ZxYz.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult<?> login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userId 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(userId));
        //用户信息存入redis
        redisCache.setCacheObject("blogLogin:"+userId,loginUser);
        //封装 token和userinfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(userLoginVo);
    }

    @Override
    public ResponseResult<?> logout() {

        //获取token 解析得 userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //获取userid
        Long userId = loginUser.getUser().getId();

        // 删除redis中的用户信息
        redisCache.deleteObject("login"+userId);


        return ResponseResult.okResult();
    }
}
