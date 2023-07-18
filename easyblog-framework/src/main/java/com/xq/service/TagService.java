package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.TagListDto;
import com.xq.domain.entity.Tag;
import com.xq.domain.vo.PageVo;
import com.xq.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-06-05 20:47:29
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);


    ResponseResult addFlag(Tag tag);

    List<TagVo> listAllTag();

    ResponseResult deleteTagByIds(List<Long> idList);

    ResponseResult getTagById(long tagId);

    ResponseResult putTag(Tag tag);
}

