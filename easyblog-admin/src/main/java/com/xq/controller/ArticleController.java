package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddArticleDto;
import com.xq.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping()
    public ResponseResult addArticle(@RequestBody AddArticleDto articleDto) {
        return articleService.addArticle(articleDto);
    }
}
