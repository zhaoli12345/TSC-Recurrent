package com.cdut.current.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("malaysia_map")
public class MalaysiaMap {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "area_id")
    private Long areaId;

    @TableField(value = "lat")
    private float lat;

    @TableField(value = "lon")
    private float lon;

    @TableField(value = "map_name")
    private String mapName;
}
