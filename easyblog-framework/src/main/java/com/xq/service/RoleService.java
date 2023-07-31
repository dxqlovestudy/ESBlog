package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddRoleDto;
import com.xq.domain.dto.ChangeRoleStatusDto;
import com.xq.domain.dto.ListRoleDto;
import com.xq.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-06-05 21:45:53
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    ResponseResult listRole(Integer pageNum, Integer pageSize, ListRoleDto listRoleDto);

    ResponseResult changeStatus(ChangeRoleStatusDto changeRoleStatusDto);

    ResponseResult addRole(AddRoleDto addRoleDto);

    ResponseResult getRoleById(Long menuId);


    ResponseResult deleteRoleById(List<Long> roleIds);

    ResponseResult listAllRole();
}

