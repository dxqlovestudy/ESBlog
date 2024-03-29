package com.xq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuListDto {
    //菜单名称
    private String menuName;
    //菜单状态（0正常 1停用）
    private String status;
}
