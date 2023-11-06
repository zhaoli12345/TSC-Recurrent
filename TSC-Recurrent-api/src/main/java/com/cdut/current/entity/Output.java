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
@TableName("output")
public class Output {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "area_id")
    private Long areaId;

    @TableField(value = "lithology_pattern")
    private String lithologyPattern;

    @TableField(value = "facies_level")
    private String faciesLevel;

    @TableField(value = "is_relative")
    private Boolean isRelative;

    @TableField(value = "is_primary")
    private Boolean isPrimary;

    @TableField(value = "ma_formula")
    private String maFormula;   //年龄公式

    @TableField(value = "descr")
    private String descr;

    @TableField(value = "combined_comments")
    private String combinedComments;

    @TableField(value = "lithology")
    private String lithology;

    @TableField(value = "calibration_comments")
    private String calibrationComments;

    @TableField(exist = false)
    private Float age;
}
