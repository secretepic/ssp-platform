package com.boyu.vo.system;

import lombok.Data;

import java.util.List;

@Data
public class MenuTreeVo {

    private Long id;

    private String name;

    private List<MenuTreeVo> children;
}
