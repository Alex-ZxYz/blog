package com.ZxYz.service.Impl;

import com.ZxYz.constants.SystemConstants;
import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Article;
import com.ZxYz.domain.entity.Category;
import com.ZxYz.domain.vo.ArticleDetailVo;
import com.ZxYz.domain.vo.ArticleListVo;
import com.ZxYz.domain.vo.HotArticleVo;
import com.ZxYz.domain.vo.PageVo;
import com.ZxYz.mapper.ArticleMapper;
import com.ZxYz.service.ArticleService;
import com.ZxYz.service.CategoryService;
import com.ZxYz.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseResult<?> hotArticleList() {
        //查询热门文章 返回Result

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        // 不展示草稿、已删除、按浏览量进行降序排列 前10
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);


        page(page,queryWrapper);
        List<Article> articles = page.getRecords();


        //bean拷贝
        // for (Article article : articles) {
        //     HotArticleVo vo = new HotArticleVo();
        //     BeanUtils.copyProperties(article,vo);
        //     articleVos.add(vo);
        // }

        //bean 拷贝
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult<?> articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //如果有 分类id 则按照分类id进行查询 (id非空 且大于0)
        if (null != categoryId && categoryId >0){
            queryWrapper.eq(Article::getCategoryId,categoryId);
        }
        //状态 正式发布
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);

        //置顶文章在前 （按照 isTop进行降序排列）
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        //查询categoryName
        List<Article> articles = page.getRecords();

        // for (Article article : articles) {
        //     Category category = categoryService.getById(article.getCategoryId());
        //     article.setCategoryName(category.getName());
        // }

        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult<?> getArticleDetail(Long id) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //根据 id 查询文章
        Article article = getById(id);
        //转换vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //根据分类id查询分类名称
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
           articleDetailVo.setCategoryName(category.getName());
        }

        //封装返回

        return ResponseResult.okResult(articleDetailVo);
    }


}
