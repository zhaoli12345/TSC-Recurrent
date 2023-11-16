package com.cdut.current.vo;

import com.cdut.current.entity.MasterChronos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasterSpotVO extends SpotVO{

    private String eon;

    private String era;

    private String period;

    private String subPeriod;

    private String epoch;

    private String subEpoch;

    private String stage;

    private String subStage;

    private Float ma;

    private String comments;

    public MasterSpotVO(MasterChronos masterChronos){
        super(masterChronos);

        this.eon=masterChronos.getEon();
        this.era=masterChronos.getEra();
        this.period=masterChronos.getPeriod();
        this.subPeriod=masterChronos.getSubPeriod();
        this.epoch=masterChronos.getEpoch();
        this.subEpoch=masterChronos.getSubEpoch();
        this.stage=masterChronos.getStage();
        this.subStage=masterChronos.getSubStage();
        this.ma=masterChronos.getMa();
        this.comments=masterChronos.getComments();
    }
}
