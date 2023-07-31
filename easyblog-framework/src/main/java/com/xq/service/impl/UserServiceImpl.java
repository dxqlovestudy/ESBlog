package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.constants.SystemConstants;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddUserDto;
import com.xq.domain.dto.ChangeUserStatusVo;
import com.xq.domain.dto.ListUserDto;
import com.xq.domain.dto.PutUserDto;
import com.xq.domain.entity.Role;
import com.xq.domain.entity.User;
import com.xq.domain.entity.UserRole;
import com.xq.domain.vo.GetUserRoleInfoByIdVo;
import com.xq.domain.vo.PageVo;
import com.xq.domain.vo.UserInfoVo;
import com.xq.domain.vo.UserVo;
import com.xq.enums.AppHttpCodeEnum;
import com.xq.exception.SystemException;
import com.xq.mapper.UserMapper;
import com.xq.service.RoleService;
import com.xq.service.UserRoleService;
import com.xq.service.UserService;
import com.xq.utils.BeanCopyUtils;
import com.xq.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-31 22:04:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult getuserInfo() {
        // 获取当前用户
        Long userId = SecurityUtils.getUserId();

        // 根据userId查询用户信息
        User user = getById(userId);

        // 封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName()))
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        if (!StringUtils.hasText(user.getPassword()))
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        if (!StringUtils.hasText(user.getNickName()))
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        if (!StringUtils.hasText(user.getEmail()))
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);

        // 对数据是否存在进行判断
        // 对用户名是否存在判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        // 对昵称是否存在进行判断
        if (nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        // 对邮箱是否存在进行判断
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        // 对密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        // 存入数据库
        save(user);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserList(Integer pageNum, Integer pageSize, ListUserDto listUserDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(listUserDto.getUserName()), User::getUserName, listUserDto.getUserName());
        queryWrapper.like(StringUtils.hasText(listUserDto.getPhonenumber()), User::getPhonenumber, listUserDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(listUserDto.getStatus()), User::getStatus, listUserDto.getStatus());
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(ChangeUserStatusVo changeUserStatusVo) {
        User user = BeanCopyUtils.copyBean(changeUserStatusVo, User.class);
        String userId = changeUserStatusVo.getUserId();
        user.setId(Long.parseLong(userId));
        getBaseMapper().updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addUser(AddUserDto addUserDto) {
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 相较于getBaseMapper()更推荐使用save()方法，因为save更加简洁、易读。
        // 原因：1.避免直接依赖底层 Mapper 对象。2.更符合面向对象的思想。3.提供更多的便利功能
//        getBaseMapper().insert(user);
        save(user);
        List<UserRole> userRoleList = addUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), Long.valueOf(roleId)))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUserByIds(List<Long> userIds) {
        removeByIds(userIds);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserRoleInfoById(long userId) {
        LambdaQueryWrapper<UserRole> userRoleQueryWrapper = new LambdaQueryWrapper();
        userRoleQueryWrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoleList = userRoleService.list(userRoleQueryWrapper);
        List<String> roleIds = userRoleList.stream()
                .map(userRole -> userRole.getRoleId().toString())
                .collect(Collectors.toList());
        LambdaQueryWrapper<Role> roleQueryWrapper = new LambdaQueryWrapper<>();
        roleQueryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> roles = roleService.list(roleQueryWrapper);
        User user = getById(userId);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        GetUserRoleInfoByIdVo getUserRoleInfoByIdVo = new GetUserRoleInfoByIdVo(roleIds, roles, userVo);
        return ResponseResult.okResult(getUserRoleInfoByIdVo);
    }

    @Override
    public ResponseResult putUserInfoById(PutUserDto putUserDto) {
        User user = BeanCopyUtils.copyBean(putUserDto, User.class);
        long userId = Long.parseLong(putUserDto.getId());
        user.setId(userId);
        updateById(user);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserRole::getUserId, userId);
        // 先移除原来的权限，再添加新的权限。
        userRoleService.remove(queryWrapper);
        List<UserRole> userRoleList = putUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(userId, Long.parseLong(roleId)))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }
}

