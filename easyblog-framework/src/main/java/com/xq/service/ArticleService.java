package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.dto.AddArticleDto;
import com.xq.domain.dto.ListArticleDto;
import com.xq.domain.entity.Article;
import com.xq.domain.ResponseResult;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();


    ResponseResult getArticleListByCategoryId(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetailById(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto articleDto);

    ResponseResult listArticle(Integer pageNum, Integer pageSize, ListArticleDto listArticleDto);

    ResponseResult deleteArticleByIds(List<Long> articleIds);

    ResponseResult getArticleById(Long articleId);
}
