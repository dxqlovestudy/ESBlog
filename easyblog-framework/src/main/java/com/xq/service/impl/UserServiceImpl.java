package com.xq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.domain.ResponseResult;
import com.xq.domain.entity.User;
import com.xq.domain.vo.UserInfoVo;
import com.xq.mapper.UserMapper;
import com.xq.service.UserService;
import com.xq.utils.BeanCopyUtils;
import com.xq.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-31 22:04:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult getuserInfo() {
        // 获取当前用户
        Long userId = SecurityUtils.getUserId();

        // 根据userId查询用户信息
        User user = getById(userId);

        // 封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}

