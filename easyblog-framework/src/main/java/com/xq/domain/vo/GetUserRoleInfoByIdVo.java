package com.xq.domain.vo;

import com.xq.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRoleInfoByIdVo {
    List<String> roleIds;
    List<Role> roles;
    UserVo user;
}
