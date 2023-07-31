package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddUserDto;
import com.xq.domain.dto.ChangeUserStatusVo;
import com.xq.domain.dto.ListUserDto;
import com.xq.domain.dto.PutUserDto;
import com.xq.domain.entity.User;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-05-31 22:04:25
 */
public interface UserService extends IService<User> {

    ResponseResult getuserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult getUserList(Integer pageNum, Integer pageSize, ListUserDto listUserDto);

    ResponseResult changeStatus(ChangeUserStatusVo changeUserStatusVo);

    ResponseResult addUser(AddUserDto addUserDto);

    ResponseResult deleteUserByIds(List<Long> userIds);

    ResponseResult getUserRoleInfoById(long userId);

    ResponseResult putUserInfoById(PutUserDto putUserDto);
}

