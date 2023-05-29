package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
