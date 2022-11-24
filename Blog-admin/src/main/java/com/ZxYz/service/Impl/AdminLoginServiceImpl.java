package com.ZxYz.service.Impl;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.LoginUser;
import com.ZxYz.domain.entity.User;
import com.ZxYz.domain.vo.UserInfoVo;
import com.ZxYz.domain.vo.UserLoginVo;
import com.ZxYz.service.AdminLoginService;
import com.ZxYz.utils.BeanCopyUtils;
import com.ZxYz.utils.JwtUtil;
import com.ZxYz.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult<?> adminLogin(User user) {
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
        redisCache.setCacheObject("adminLogin:"+userId,loginUser);


        Map<String,String> map =new HashMap<>();
        map.put("token",jwt);

        return ResponseResult.okResult(map);

    }
}
