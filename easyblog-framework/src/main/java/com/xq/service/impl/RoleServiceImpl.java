package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.constants.SystemConstants;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddRoleDto;
import com.xq.domain.dto.ChangeRoleStatusDto;
import com.xq.domain.dto.ListRoleDto;
import com.xq.domain.entity.Role;
import com.xq.domain.entity.RoleMenu;
import com.xq.domain.vo.PageVo;
import com.xq.mapper.RoleMapper;
import com.xq.service.RoleMenuService;
import com.xq.service.RoleService;
import com.xq.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-06-05 21:45:54
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    RoleService roleService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
        //查询用户具有的角色信息
    }

    @Override
    public ResponseResult listRole(Integer pageNum, Integer pageSize, ListRoleDto listRoleDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(listRoleDto.getRoleName()), Role::getRoleName, listRoleDto.getRoleName());
        queryWrapper.eq(StringUtils.hasText(listRoleDto.getStatus()), Role::getStatus, listRoleDto.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page(pageNum, pageSize);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(ChangeRoleStatusDto changeRoleStatusDto) {
        Role role = BeanCopyUtils.copyBean(changeRoleStatusDto, Role.class);
        // 这里因为ChangeRoleStatusDto类中的Id为String类型，但是Role类中的id为Long属性，所以需要类型转换
        long roleId = Long.parseLong(changeRoleStatusDto.getRoleId());
        role.setId(roleId);
        getBaseMapper().updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        Long roleId = role.getId();
        List<RoleMenu> roleMenuList = new ArrayList<>();
        for (Long menuId:
             addRoleDto.getMenuIds()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        roleMenuService.saveBatch(roleMenuList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long menuId) {
        Role role = baseMapper.selectById(menuId);
        return ResponseResult.okResult(role);
    }

    @Override
    public ResponseResult deleteRoleById(List<Long> roleIds) {
        getBaseMapper().deleteBatchIds(roleIds);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roleList = roleService.list(queryWrapper);
        return ResponseResult.okResult(roleList);
    }
}
