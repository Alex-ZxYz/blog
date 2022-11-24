package com.ZxYz.job;

import com.ZxYz.domain.entity.Article;
import com.ZxYz.service.ArticleService;
import com.ZxYz.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/50 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> articleViewCountMap = redisCache.getCacheMap("article:viewCount");

        //map转换
        List<Article> articles = articleViewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //更新至数据库
        articleService.updateBatchById(articles);
    }
}
