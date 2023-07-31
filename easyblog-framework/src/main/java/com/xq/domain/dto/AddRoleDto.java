package com.xq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    private List<Long> menuIds;
    private String remark;
}
