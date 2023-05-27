package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.entity.Article;
import com.xq.domain.ResponseResult;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();


    ResponseResult getArticleListByCategoryId(Integer pageNum, Integer pageSize, Long categoryId);
}
