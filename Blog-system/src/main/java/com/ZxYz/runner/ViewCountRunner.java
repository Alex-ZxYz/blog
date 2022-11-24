package com.ZxYz.runner;

import com.ZxYz.domain.entity.Article;
import com.ZxYz.mapper.ArticleMapper;
import com.ZxYz.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 定时任务
 * 每十分钟将redis中浏览量数据更新至数据库中
 */

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisCache redisCache;


    @Override
    public void run(String... args) throws Exception{
        //查询博客信息 id -> viewCount
        //查询所有文章信息
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));

        //存储到redis
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
