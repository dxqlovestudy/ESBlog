package com.xq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.constants.SystemConstants;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddMenuDto;
import com.xq.domain.dto.MenuListDto;
import com.xq.domain.entity.Menu;
import com.xq.domain.vo.MenuListVo;
import com.xq.domain.vo.MenuVo;
import com.xq.enums.AppHttpCodeEnum;
import com.xq.mapper.MenuMapper;
import com.xq.service.MenuService;
import com.xq.utils.BeanCopyUtils;
import com.xq.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-06-05 21:48:26
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 判断是否是管理员 如果是管理员返回集合中只需要admin
        if (id.equals(1L)) {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream().map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
            /*List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;*/
        }
        // 这里应该使用的是baseMapper的方法，不是重写的方法
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            // 如果是 返回所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 否则 获取用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建tree结构，子父结构
        // 先找出第一层菜单，再找出他们的子菜单，设置到children属性中
        List<Menu> menuTree = buliderMenuTree(menus, 0L);
        return menuTree;
    }

    @Override
    public ResponseResult getMenuList(MenuListDto menuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.hasText(menuListDto.getMenuName()), Menu::getMenuName, menuListDto.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menuListDto.getStatus()), Menu::getStatus, menuListDto.getStatus());
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(menus, MenuListVo.class);
        return ResponseResult.okResult(menuListVos);
    }

    @Override
    public ResponseResult addMenu(AddMenuDto addMenuDto) {
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        getBaseMapper().insert(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenuById(Long menuId) {
        // 判断是否有子菜单
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", menuId);
        int childCount = count(queryWrapper);
        // 有子菜单就提示错误
        if (childCount > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SUBMENUS_NOT_NULL);
        } else {
            // 无子菜单就删除
            getBaseMapper().deleteById(menuId);
            return ResponseResult.okResult();
        }
    }

    @Override
    public ResponseResult getMenuById(Long menuId) {
        Menu menu = getBaseMapper().selectById(menuId);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult putMenu(AddMenuDto addMenuDto) {
        Menu menu = BeanCopyUtils.copyBean(addMenuDto, Menu.class);
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARENT_MENU_CANNOT_BE_ITSELF);
        } else {
            getBaseMapper().updateById(menu);
            return ResponseResult.okResult();
        }
    }

    private List<Menu> buliderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 从menus中获取传入参数的子Menu
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}

