package com.xq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.domain.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-05 21:45:51
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(@Param("userId") Long userId);
}

