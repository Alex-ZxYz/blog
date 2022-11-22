package com.ZxYz.service;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文章表(Article)表服务接口
 *
 * @author ZxYz
 * @since 2022-11-08 23:37:37
 */
public interface ArticleService extends IService<Article> {

    ResponseResult<?> hotArticleList();

    ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult<?> getArticleDetail(Long id);
}

