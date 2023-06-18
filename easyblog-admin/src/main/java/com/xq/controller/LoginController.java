package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.entity.LoginUser;
import com.xq.domain.entity.Menu;
import com.xq.domain.entity.User;
import com.xq.domain.vo.AdminUserInfoVo;
import com.xq.domain.vo.RoutersVo;
import com.xq.domain.vo.UserInfoVo;
import com.xq.enums.AppHttpCodeEnum;
import com.xq.exception.SystemException;
import com.xq.service.LoginService;
import com.xq.service.MenuService;
import com.xq.service.RoleService;
import com.xq.utils.BeanCopyUtils;
import com.xq.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            // 提示：必须要传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        // 获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        // 封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

   @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 查询menu，结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);

        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}
