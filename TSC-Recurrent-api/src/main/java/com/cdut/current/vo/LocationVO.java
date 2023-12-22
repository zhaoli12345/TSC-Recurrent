package com.cdut.current.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationVO {
    private int id;

    private int areaId;

    private String areaName;

    private float lon; //经度

    private float lat; //纬度

    private String imagePath;//图片路径

    private float age;
}
