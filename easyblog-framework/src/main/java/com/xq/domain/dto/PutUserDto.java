package com.xq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutUserDto {
    //邮箱
    private String email;
    //主键@TableId
    private String id;
    //昵称
    private String nickName;
    //用户性别（0男，1女，2未知）
    private String sex;
    //账号状态（0正常 1停用）
    private String status;
    //用户名
    private String userName;
    List<String> roleIds;
}
