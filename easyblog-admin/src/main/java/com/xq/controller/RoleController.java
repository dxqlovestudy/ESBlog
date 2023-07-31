package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddRoleDto;
import com.xq.domain.dto.ChangeRoleStatusDto;
import com.xq.domain.dto.ListRoleDto;
import com.xq.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @GetMapping("/list")
    public ResponseResult listRole(Integer pageNum, Integer pageSize, ListRoleDto listRoleDto) {
        return roleService.listRole(pageNum, pageSize, listRoleDto);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        return roleService.changeStatus(changeRoleStatusDto);
    }
    @PostMapping()
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }
    @GetMapping("{id}")
    public ResponseResult getRoleById(@PathVariable String id) {
        Long menuId = Long.parseLong(id);
        return roleService.getRoleById(menuId);
    }
    @DeleteMapping("{ids}")
    public ResponseResult deleteRoleById(@PathVariable String ids) {
        List<Long> roleIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return roleService.deleteRoleById(roleIds);
    }
}