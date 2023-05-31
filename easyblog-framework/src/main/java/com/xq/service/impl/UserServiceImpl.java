package com.xq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.domain.entity.User;
import com.xq.mapper.UserMapper;
import com.xq.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-31 22:04:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

