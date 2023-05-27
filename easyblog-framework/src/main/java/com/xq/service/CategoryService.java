package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.entity.Category;
import com.xq.domain.ResponseResult;
import com.xq.domain.vo.CategoryVo;

import java.util.List;

public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
