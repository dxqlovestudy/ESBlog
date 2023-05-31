package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.domain.ResponseResult;
import com.xq.domain.entity.Comment;
import com.xq.domain.vo.CommentVo;
import com.xq.domain.vo.PageVo;
import com.xq.mapper.CommentMapper;
import com.xq.service.CommentService;
import com.xq.service.UserService;
import com.xq.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // 根据文章id查询根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 对articleId进行判断
        queryWrapper.eq(Comment::getArticleId, articleId);
        // 根评论 rootId = -1
        queryWrapper.eq(Comment::getRootId, -1);

        // 分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
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
