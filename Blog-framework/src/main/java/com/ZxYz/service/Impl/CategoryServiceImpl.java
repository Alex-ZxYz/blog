package com.ZxYz.service.Impl;

import com.ZxYz.constants.SystemConstants;
import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Article;
import com.ZxYz.domain.entity.Category;
import com.ZxYz.domain.vo.CategoryVo;
import com.ZxYz.mapper.CategoryMapper;
import com.ZxYz.service.ArticleService;
import com.ZxYz.service.CategoryService;
import com.ZxYz.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{

    @Autowired
    private ArticleService articleService;

    /**
     *
     *  分类
     * @return
     */
    @Override
    public ResponseResult<?> getCategoryList() {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();

        //查找发布（状态）正常文章 去重 按照 Category_id 分类
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        //Id收集去重
        Set<Long> categoryIds =  articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());

        //Id过滤
       List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories,CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }



}
