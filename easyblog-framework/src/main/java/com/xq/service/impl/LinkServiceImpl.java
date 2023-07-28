package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.constants.SystemConstants;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddLinkDto;
import com.xq.domain.dto.ListLinkDto;
import com.xq.domain.entity.Link;
import com.xq.domain.vo.LinkVo;
import com.xq.domain.vo.PageVo;
import com.xq.mapper.LinkMapper;
import com.xq.service.LinkService;
import com.xq.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public ResponseResult getAllLink() {
        // 查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // 转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        // 封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult<PageVo> listLink(Integer pageNum, Integer pageSize, ListLinkDto listLinkDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(listLinkDto.getName()), Link::getName, listLinkDto.getName());
        queryWrapper.eq(StringUtils.hasText(listLinkDto.getStatus()), Link::getStatus, listLinkDto.getStatus());
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        getBaseMapper().insert(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLinkByIds(List<Long> linkList) {
        getBaseMapper().deleteBatchIds(linkList);
        return ResponseResult.okResult();
    }
}
