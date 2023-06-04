package com.xq.job;

import com.xq.constants.SystemConstants;
import com.xq.domain.entity.Article;
import com.xq.service.ArticleService;
import com.xq.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob{

    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleService articleService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void UpdateViewCountJob() {
        // 获取Redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_ARTICLE_COUNT);

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()),
                        entry.getValue().longValue()))
                .collect(Collectors.toList());

        // 更新到数据库
        articleService.updateBatchById(articles);
    }
}
