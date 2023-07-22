package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.dto.CategoryDto;
import com.xq.domain.entity.Category;
import com.xq.domain.ResponseResult;
import com.xq.domain.vo.CategoryVo;
import com.xq.domain.vo.PageVo;

import java.util.List;

public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult<PageVo> pageCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto);

    ResponseResult addCategory(CategoryDto category);
}
