package com.xq.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleStatusDto {
    //角色ID
    private String roleId;
    //角色状态（0正常 1停用）
    private String status;

    // 覆盖原始的setId方法，将String类型的roleId转换成Long类型
//    public void setId(String roleId) {
//        this.id = Long.parseLong(roleId);
//    }
}
