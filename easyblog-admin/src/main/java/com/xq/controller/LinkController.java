package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddLinkDto;
import com.xq.domain.dto.ListLinkDto;
import com.xq.domain.vo.PageVo;
import com.xq.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    LinkService linkService;
    @GetMapping("/list")
    public ResponseResult<PageVo> listLink(Integer pageNum, Integer pageSize, ListLinkDto listLinkDto) {
        /**
         * @description: 分页展示或查询Link内容，可以根据名称模糊查询，审核状态查询
         * @param pageNum
         * @param pageSize
         * @param listLinkDto
         * @return com.xq.domain.ResponseResult<com.xq.domain.vo.PageVo>
         * @author: HuaXian
         * @date: 2023/7/28 10:16
         */
        return linkService.listLink(pageNum, pageSize, listLinkDto);
    }
    @PostMapping()
    public ResponseResult addLink(@RequestBody AddLinkDto addLinkDto) {
        return linkService.addLink(addLinkDto);
    }
    @DeleteMapping("{ids}")
    public ResponseResult deleteLinkByIds(@PathVariable("ids") String ids) {
        List<Long> linkList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return linkService.deleteLinkByIds(linkList);
    }

}
