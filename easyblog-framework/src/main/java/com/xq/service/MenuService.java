package com.xq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddMenuDto;
import com.xq.domain.dto.MenuListDto;
import com.xq.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-06-05 21:48:26
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(MenuListDto menuListDto);

    ResponseResult addMenu(AddMenuDto addMenuDto);

    ResponseResult deleteMenuById(Long menuId);

    ResponseResult getMenuById(Long menuId);

    ResponseResult putMenu(AddMenuDto addMenuDto);
}

