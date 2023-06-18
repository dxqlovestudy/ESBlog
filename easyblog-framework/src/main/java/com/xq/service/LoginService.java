package com.xq.service;

import com.xq.domain.ResponseResult;
import com.xq.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
