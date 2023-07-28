package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddArticleDto;
import com.xq.domain.dto.ListArticleDto;
import com.xq.domain.entity.Article;
import com.xq.domain.vo.PageVo;
import com.xq.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping()
    public ResponseResult addArticle(@RequestBody AddArticleDto articleDto) {
        return articleService.addArticle(articleDto);
    }

    @GetMapping("list")
    public ResponseResult<PageVo> listArticle(Integer pageNum, Integer pageSize, ListArticleDto listArticleDto) {
        return articleService.listArticle(pageNum, pageSize, listArticleDto);
    }

    @DeleteMapping("{ids}")
    public ResponseResult deleteArticleByIds(@PathVariable("ids") String ids) {
        List<Long> articleIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return articleService.deleteArticleByIds(articleIds);
    }

    @GetMapping("{id}")
    public ResponseResult getArticleById(@PathVariable("id") String id) {
        Long articleId= Long.parseLong(id);
        return articleService.getArticleById(articleId);
    }

    @PutMapping()
    public ResponseResult putArticleById(@RequestBody AddArticleDto addArticleDto) {
        // TODO 内容管理-文章管理-修改文章，并且put进去。和post文章还是有一点点区别
        return articleService.putArticleById(addArticleDto);
    }
}
