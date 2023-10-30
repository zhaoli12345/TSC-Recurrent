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
@TableName("malaysia_chronostratigraphic")
public class MasterChronos {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "eon")
    private String eon;

    @TableField(value = "era")
    private String era;

    @TableField(value = "period")
    private String period;

    @TableField(value = "sub_period")
    private String subPeriod;

    @TableField(value = "epoch")
    private String epoch;

    @TableField(value = "sub_epoch")
    private String subEpoch;

    @TableField(value = "stage")
    private String stage;

    @TableField(value = "sub_stage")
    private String subStage;

    @TableField(value = "ma")    /*I am a pig!*/
    private Float ma;

    @TableField(value = "comments")
    private String comments;
}
