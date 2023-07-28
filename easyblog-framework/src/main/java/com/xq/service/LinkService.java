package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddLinkDto;
import com.xq.domain.dto.ListLinkDto;
import com.xq.domain.entity.Link;
import com.xq.domain.vo.PageVo;

import java.util.List;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();

    ResponseResult<PageVo> listLink(Integer pageNum, Integer pageSize, ListLinkDto listLinkDto);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult deleteLinkByIds(List<Long> linkList);
}
