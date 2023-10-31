package com.cdut.current.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaVO {

    private Long id;

    private String areaName;

    private Long parentId;

    private String descr;

    private Boolean leaf;  //是否是叶子结点

    private List<AreaVO> childrenArea;
}
