package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.TagListDto;
import com.xq.domain.entity.Tag;
import com.xq.domain.vo.PageVo;
import com.xq.domain.vo.TagVo;
import com.xq.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    public ResponseResult addFlag(@RequestBody Tag tag) {
        return tagService.addFlag(tag);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        List<TagVo> allTagList = tagService.listAllTag();
        return ResponseResult.okResult(allTagList);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult deleteTagByIds(@PathVariable("ids") String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return tagService.deleteTagByIds(idList);
    }

    @GetMapping("{id}")
    public ResponseResult getTagById(@PathVariable("id") String id) {
        long tagId = Long.parseLong(id);
        return tagService.getTagById(tagId);
    }

    @PutMapping()
    public ResponseResult putTag(@RequestBody Tag tag) {
        return tagService.putTag(tag);
    }
}
