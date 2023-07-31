package com.xq.domain.vo;

import com.xq.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeVo {
    private List<MenuTreeVo> children;
    private Long id;
    private String label;
    private Long parentId;
}
