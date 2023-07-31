package com.xq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.domain.entity.RoleMenu;
import com.xq.mapper.RoleMenuMapper;
import com.xq.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-07-30 21:28:00
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

