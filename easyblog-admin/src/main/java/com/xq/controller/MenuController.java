package com.xq.controller;

import com.xq.domain.ResponseResult;
import com.xq.domain.dto.AddMenuDto;
import com.xq.domain.dto.MenuListDto;
import com.xq.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @GetMapping("/list")
    public ResponseResult getMenuList(MenuListDto menuListDto) {
        return menuService.getMenuList(menuListDto);
    }
    @PostMapping()
    public ResponseResult addMenu(@RequestBody AddMenuDto addMenuDto) {
        return menuService.addMenu(addMenuDto);
    }
    @DeleteMapping("{id}")
    public ResponseResult deleteMenuById(@PathVariable("id") String id) {
        Long menuId = Long.parseLong(id);
        return menuService.deleteMenuById(menuId);
    }
    @GetMapping("{id}")
    public ResponseResult getMenuById(@PathVariable("id") String id) {
        Long roleId = Long.parseLong(id);
        return menuService.getMenuById(roleId);
    }
    @PutMapping()
    public ResponseResult putMenu(@RequestBody AddMenuDto addMenuDto) {
        return menuService.putMenu(addMenuDto);
    }
    @GetMapping("/treeselect")
    public ResponseResult treeSelectMenu() {
        return menuService.treeSelectMenu();
    }
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult treeSelectMenuById(@PathVariable("id") String id) {
        long menuId = Long.parseLong(id);
        return menuService.treeSelectMenuById(menuId);
    }
}
