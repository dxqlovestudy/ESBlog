package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.CategoryListDto;
import com.xq.domain.entity.Tag;
import com.xq.domain.vo.CategoryVo;
import com.xq.domain.vo.PageVo;
import com.xq.domain.vo.TagVo;
import com.xq.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("list")
    public ResponseResult<PageVo> listCategoryPage(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        return categoryService.pageCategoryList(pageNum, pageSize, categoryListDto);
    }


}
