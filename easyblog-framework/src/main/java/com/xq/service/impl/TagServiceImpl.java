package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.TagListDto;
import com.xq.domain.entity.Tag;
import com.xq.domain.vo.PageVo;
import com.xq.domain.vo.TagVo;
import com.xq.mapper.TagMapper;
import com.xq.service.TagService;
import com.xq.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-06-05 20:47:29
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        // 封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addFlag(Tag tag) {
        getBaseMapper().insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        List<Tag> tagLists = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tagLists, TagVo.class);
        return tagVos;
    }

    @Override
    public ResponseResult deleteTagByIds(List<Long> idList) {
        getBaseMapper().deleteBatchIds(idList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(long tagId) {
        Tag tag = getBaseMapper().selectById(tagId);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult putTag(Tag tag) {
        UpdateWrapper<Tag> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", tag.getId());
        updateWrapper.set("name", tag.getName());
        updateWrapper.set("remark", tag.getRemark());
        getBaseMapper().update(tag, updateWrapper);
        return ResponseResult.okResult();
    }
}

