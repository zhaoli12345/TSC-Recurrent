package com.cdut.current.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdut.current.vo.SpotVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("output")
public class Output implements Comparable<Output> {
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

    @TableField(value = "combined_comments")
    private String combinedComments;

    @TableField(value = "lithology")
    private String lithology;

    @TableField(value = "calibration_comments")
    private String calibrationComments;

    @TableField(value = "reference")
    private String reference;

    @TableField(value = "percent")
    private Float percent;

    @TableField(exist = false)
    private Float age;

    @TableField(exist = false)
    private Float topAge;

    @TableField(exist = false)
    private String areaName;

    @Override
    public int compareTo(@NotNull Output o) {
        return this.getAge().compareTo(o.getAge());  // 升序排列
    }
}
