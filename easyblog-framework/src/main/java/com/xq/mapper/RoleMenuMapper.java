package com.xq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.domain.entity.RoleMenu;
import com.xq.domain.vo.MenuTreeVo;

import java.util.List;

/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-30 21:22:58
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<String> selectALLMenuIds();

    List<String> selectRoleRelateMenuIds(long roleId);

    List<MenuTreeVo> selectALLMenuTreeVo();

    List<MenuTreeVo> selectMenuTreeVoByRoleId(long roleId);
}

