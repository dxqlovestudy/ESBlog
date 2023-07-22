package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.constants.SystemConstants;
import com.xq.domain.dto.CategoryDto;
import com.xq.domain.entity.Article;
import com.xq.domain.entity.Category;
import com.xq.domain.ResponseResult;
import com.xq.domain.vo.CategoryVo;
import com.xq.domain.vo.PageVo;
import com.xq.domain.vo.ReviceCategoryVo;
import com.xq.mapper.CategoryMapper;
import com.xq.service.ArticleService;
import com.xq.service.CategoryService;
import com.xq.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult<PageVo> pageCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(categoryDto.getName()), Category::getName, categoryDto.getName());
        queryWrapper.eq(StringUtils.hasText(categoryDto.getStatus()), Category::getStatus, categoryDto.getStatus());
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        String categoryName = categoryDto.getName();
        Category existingCategory = findByCategoryName(categoryName);
        if (existingCategory != null) {
            existingCategory.setDescription(categoryDto.getDescription());
            existingCategory.setStatus(categoryDto.getStatus());
            getBaseMapper().updateById(existingCategory);
        } else {
            Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
            getBaseMapper().insert(category);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(long categoryId) {
        Category category = getBaseMapper().selectById(categoryId);
        ReviceCategoryVo reviceCategoryVo = BeanCopyUtils.copyBean(category, ReviceCategoryVo.class);
        return ResponseResult.okResult(reviceCategoryVo);
    }

    @Override
    public ResponseResult addCategoryById(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        getBaseMapper().updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delectCategoryByIds(List<Long> idList) {
        getBaseMapper().deleteBatchIds(idList);
        return ResponseResult.okResult();
    }

    private Category findByCategoryName(String categoryName) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,categoryName);
        return categoryMapper.selectOne(queryWrapper);
    }
}



