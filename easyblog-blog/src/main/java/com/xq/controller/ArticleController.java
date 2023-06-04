package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.vo.PageVo;
import com.xq.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
//        ResponseResult result = articleService.hotArticleList();
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult<PageVo> articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.getArticleListByCategoryId(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetailById(@PathVariable("id") Long id) {
        return articleService.getArticleDetailById(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id) {
        return articleService.updateViewCount(id);
    }
}
