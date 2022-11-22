package com.ZxYz.controller;

import com.ZxYz.constants.SystemConstants;
import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Comment;
import com.ZxYz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult<?> commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    @GetMapping ("/linkCommentList")
    public ResponseResult<?> linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT, null,pageNum,pageSize);
    }


    @PostMapping
    public ResponseResult<?> addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
