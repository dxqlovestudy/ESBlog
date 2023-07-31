package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddUserDto;
import com.xq.domain.dto.ChangeUserStatusVo;
import com.xq.domain.dto.ListUserDto;
import com.xq.domain.dto.PutUserDto;
import com.xq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/list")
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, ListUserDto listUserDto) {
        /**
         * @description: 主页面分页展示，根据用户名称或者手机号码模糊查找，根据状态精确查找
         * @param pageNum
         * @param pageSize
         * @param listUserDto
         * @return com.xq.domain.ResponseResult
         * @author: HuaXian
         * @date: 2023/7/31 13:42
         */
        return userService.getUserList(pageNum, pageSize, listUserDto);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeUserStatusVo changeUserStatusVo) {
        return userService.changeStatus(changeUserStatusVo);
    }
    @PostMapping()
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }
    @DeleteMapping("{ids}")
    public ResponseResult deleteUserByIds(@PathVariable("ids") String ids) {
        List<Long> userIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return userService.deleteUserByIds(userIds);
    }
    @GetMapping("{id}")
    public ResponseResult getUserRoleInfoById(@PathVariable("id") String id) {
        /**
         * @description: 根据UserId获取User信息和Role信息，用于展示添加用户是角色信息的回显
         * @param id
         * @return com.xq.domain.ResponseResult
         * @author: HuaXian
         * @date: 2023/7/31 16:21
         */
        long userId = Long.parseLong(id);
        return userService.getUserRoleInfoById(userId);
    }
    @PutMapping()
    public ResponseResult putUserInfoById(@RequestBody PutUserDto putUserDto) {
        return userService.putUserInfoById(putUserDto);
    }
}
