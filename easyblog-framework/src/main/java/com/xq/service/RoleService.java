package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}

