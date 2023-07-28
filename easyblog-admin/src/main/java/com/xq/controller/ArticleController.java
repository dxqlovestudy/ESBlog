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
        /**
         * @description: 写博客
         * @param articleDto    请求体中的参数
         * @return com.xq.domain.ResponseResult
         * @author: HuaXian
         * @date: 2023/7/28 9:45
         */
        return articleService.addArticle(articleDto);
    }

    @GetMapping("list")
    public ResponseResult<PageVo> listArticle(Integer pageNum, Integer pageSize, ListArticleDto listArticleDto) {
        /**
         * @description: 分页查询文章列表
         * @param pageNum
         * @param pageSize
         * @param listArticleDto
         * @return com.xq.domain.ResponseResult<com.xq.domain.vo.PageVo>
         * @author: HuaXian
         * @date: 2023/7/28 9:47
         */
        return articleService.listArticle(pageNum, pageSize, listArticleDto);
    }

    @DeleteMapping("{ids}")
    public ResponseResult deleteArticleByIds(@PathVariable("ids") String ids) {
        /**
         * @description: 根据传入的ids，批量删除文章
         * @param ids   可为一个，可为多个
         * @return com.xq.domain.ResponseResult
         * @author: HuaXian
         * @date: 2023/7/28 9:49
         */
        List<Long> articleIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return articleService.deleteArticleByIds(articleIds);
    }

    @GetMapping("{id}")
    public ResponseResult getArticleById(@PathVariable("id") String id) {
        /**
         * @description: 根据传入的id返回数据库中的文章信息，用于修改文章中文章内容的回显
         * @param id
         * @return com.xq.domain.ResponseResult
         * @author: HuaXian
         * @date: 2023/7/28 9:50
         */
        Long articleId= Long.parseLong(id);
        return articleService.getArticleById(articleId);
    }

    @PutMapping()
    public ResponseResult putArticleById(@RequestBody AddArticleDto addArticleDto) {
        /**
         * @description: 修改文章的更新操作，将修改的内容更新到数据库
         * @param addArticleDto
         * @return com.xq.domain.ResponseResult
         * @author: HuaXian
         * @date: 2023/7/28 9:52
         */
        return articleService.putArticleById(addArticleDto);
    }
}
