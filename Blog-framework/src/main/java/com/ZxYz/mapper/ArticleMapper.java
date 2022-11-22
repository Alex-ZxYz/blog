package com.ZxYz.mapper;

import com.ZxYz.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author ZxYz
 * @since 2022-11-08 23:37:36
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}

