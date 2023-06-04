package com.xq.runner;

import com.xq.domain.entity.Article;
import com.xq.mapper.ArticleMapper;
import com.xq.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询article表中的 id和viewCount
        List<Article> articles = articleMapper.selectList(null);

        Map<String, Integer> viewCountMap = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article ->
        {
            return article.getViewCount().intValue();
        }));


        // 存储到Redis中
        redisCache.setCacheMap("article:viewCount", viewCountMap);

    }
}
