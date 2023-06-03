package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.constants.SystemConstants;
import com.xq.domain.ResponseResult;
import com.xq.domain.entity.Comment;
import com.xq.domain.vo.CommentVo;
import com.xq.domain.vo.PageVo;
import com.xq.enums.AppHttpCodeEnum;
import com.xq.exception.SystemException;
import com.xq.mapper.CommentMapper;
import com.xq.service.CommentService;
import com.xq.service.UserService;
import com.xq.utils.BeanCopyUtils;
import com.xq.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    /**
     *
     * @param commentType 评论类型，分为文章评论和友链评论
     * @param articleId 如果为文章评论，则需要获取articleId， 为友链评论不需要articleId，
     * @param pageNum 传入现在是第几页
     * @param pageSize 一页的大小
     * @return
     */
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 根据文章id查询根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 对articleId进行判断
        // 文章评论还是友链评论的筛选，如果是commentType = ARTICLE_COMMENT才会去获取articleId
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);

        // 根评论 rootId = -1
        queryWrapper.eq(Comment::getRootId, -1);

        // 这里筛选了评论类型为文章评论或者友链评论
        queryWrapper.eq(Comment::getType, commentType);

        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        // 查询所有根评论对应的子评论集合，并且赋值给相对应的属性
        for (CommentVo commentVo :
                commentVoList) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        comment.setCreateBy(SecurityUtils.getUserId());
        // 评论的内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论id查询所有对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 遍历vo集合
        for (CommentVo commentVo:
             commentVos) {
            // 通过createBy查询用户的昵称并赋值
            // 如果toCommentUserId不为-1才进行查询
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}
