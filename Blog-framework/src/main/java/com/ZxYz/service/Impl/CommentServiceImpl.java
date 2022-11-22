package com.ZxYz.service.Impl;

import com.ZxYz.constants.SystemConstants;
import com.ZxYz.domain.ResponseResult;
import com.ZxYz.domain.entity.Comment;
import com.ZxYz.domain.vo.CommentVo;
import com.ZxYz.domain.vo.PageVo;
import com.ZxYz.enums.AppHttpCodeEnum;
import com.ZxYz.exception.SystemException;
import com.ZxYz.mapper.CommentMapper;
import com.ZxYz.service.CommentService;
import com.ZxYz.service.UserService;
import com.ZxYz.utils.BeanCopyUtils;
import com.ZxYz.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserService userService;

    @Override
    public ResponseResult<?> commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //根据文章id查询评论(若是友链评论则不查)
        lambdaQueryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //只查询根评论
        lambdaQueryWrapper.eq(Comment::getRootId,-1);

        //评论类型
        lambdaQueryWrapper.eq(Comment::getType,commentType);

        //根评论排序
        lambdaQueryWrapper.orderByDesc(Comment::getCreateTime);

        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询根评论得子评论集合 并赋值给children
        for (CommentVo commentVo : commentVoList) {
            //查询对应子评论
             List<CommentVo> children = getChildren(commentVo.getId());
             //赋值
             commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult<?> addComment(Comment comment) {
        //评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        comment.setCreateBy(SecurityUtils.getUserId());


        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论id 查询子评论集合
     * @param id 根评论Id
     * @return 子评论集合
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getRootId,id);
        lambdaQueryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(lambdaQueryWrapper);

        return toCommentVoList(list);

    }



    public List<CommentVo> toCommentVoList(List<Comment> comments){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        //遍历Vo集合
        for (CommentVo commentVo : commentVos) {
            //通过createBy查询对应用户昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
           // 查询toCommentID查询回复对象昵称并赋值
            if (commentVo.getToCommentUserId() != -1){
                //非根评论
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);

            }
        }

        return commentVos;
    }
}
