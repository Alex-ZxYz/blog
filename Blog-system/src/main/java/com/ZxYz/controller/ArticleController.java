package com.ZxYz.controller;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/article")
@Api(tags = "文章接口",description = "文章相关接口")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @ApiOperation(value = "热门文章列表",notes = "获取热门文章")
    public ResponseResult<?> hotArticleList(){
        //查询热门文章 返回Result
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @ApiOperation(value = "文章列表",notes = "获取全部文章列表")
    public ResponseResult<?> articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "文章详情",notes = "获取文章详情")
    public ResponseResult<?> getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "浏览量",notes = "文章浏览量更新")
    public ResponseResult<?> updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
