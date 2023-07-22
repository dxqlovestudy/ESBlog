package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.CategoryDto;
import com.xq.domain.entity.Category;
import com.xq.domain.vo.CategoryVo;
import com.xq.domain.vo.PageVo;
import com.xq.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult<PageVo> listCategoryPage(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        return categoryService.pageCategoryList(pageNum, pageSize, categoryDto);
    }
    @PostMapping()
    public ResponseResult addCategory(@RequestBody CategoryDto category) {
        return categoryService.addCategory(category);
    }


}
