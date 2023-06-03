package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.entity.Comment;

public interface CommentService extends IService<Comment> {
    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

}
