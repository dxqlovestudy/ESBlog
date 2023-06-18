package com.xq.service.impl;

import com.xq.domain.ResponseResult;
import com.xq.domain.entity.LoginUser;
import com.xq.domain.entity.User;
import com.xq.domain.vo.BlogUserLoginVo;
import com.xq.domain.vo.UserInfoVo;
import com.xq.service.BlogLoginService;
import com.xq.service.LoginService;
import com.xq.utils.BeanCopyUtils;
import com.xq.utils.JwtUtil;
import com.xq.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("loginService")
public class SystemLoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate))
            throw new RuntimeException("用户名或密码错误");

        // 获取userId 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        // 把token和userinfo封装,返回
        // 把User转换成UserInfoVo
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        // 获取token， 解析userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取userid
        Long userId = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject("login:" + userId);
        return ResponseResult.okResult();
    }
}
