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
@TableName("master_chronostratigraphic")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEon() {
        return eon;
    }

    public void setEon(String eon) {
        this.eon = eon;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSubPeriod() {
        return subPeriod;
    }

    public void setSubPeriod(String subPeriod) {
        this.subPeriod = subPeriod;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public String getSubEpoch() {
        return subEpoch;
    }

    public void setSubEpoch(String subEpoch) {
        this.subEpoch = subEpoch;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getSubStage() {
        return subStage;
    }

    public void setSubStage(String subStage) {
        this.subStage = subStage;
    }

    public Float getMa() {
        return ma;
    }

    public void setMa(Float ma) {
        this.ma = ma;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
