package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-05-31 22:04:25
 */
public interface UserService extends IService<User> {

    ResponseResult getuserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

