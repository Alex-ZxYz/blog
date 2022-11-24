package com.ZxYz.controller;

import com.ZxYz.constants.SystemConstants;
import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.dto.AddCommentDto;
import com.ZxYz.domain.entity.Comment;
import com.ZxYz.service.CommentService;
import com.ZxYz.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "评论接口",description = "评论相关接口")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation(value = "评论列表",notes = "获取全部评论")
    public ResponseResult<?> commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }


    @GetMapping ("/linkCommentList")
    @ApiOperation(value = "评论列表",notes = "获取友链评论")
    public ResponseResult<?> linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT, null,pageNum,pageSize);
    }


    @PostMapping
    @ApiOperation(value = "添加评论",notes = "添加评论")
    public ResponseResult<?> addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment  = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }
}
