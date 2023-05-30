package com.xq.service;

import com.xq.domain.ResponseResult;
import com.xq.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
