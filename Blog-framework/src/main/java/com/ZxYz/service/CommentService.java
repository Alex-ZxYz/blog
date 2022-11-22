package com.ZxYz.service;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommentService extends IService<Comment> {
    ResponseResult<?> commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult<?> addComment(Comment comment);

}
