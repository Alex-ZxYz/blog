package com.ZxYz.controller;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult<?> hotArticleList(){
        //查询热门文章 返回Result
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    public ResponseResult<?> articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult<?> getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}
