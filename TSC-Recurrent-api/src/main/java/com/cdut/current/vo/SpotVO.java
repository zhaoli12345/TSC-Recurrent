package com.cdut.current.vo;


import com.cdut.current.entity.MasterChronos;
import com.cdut.current.entity.Output;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotVO {
    private Long id;
    //展示名称
    private String name;

    //年龄
    private Float age;

    private Boolean isRelative; //0是绝对，1是相对

    private Boolean isMaster;//1表示当前id处于master表，0是output表

    private List<SpotVO> children = Collections.emptyList();

    private Label label;

    private Float percent;

    public SpotVO(Output output) {
        this.id = output.getId();
        this.name = output.getLithologyPattern();
        this.age = output.getAge();
        this.isRelative = output.getIsRelative();
        this.isMaster = false;
        this.percent = output.getPercent();
    }

    public SpotVO(MasterChronos masterChronos) {
        this.id = masterChronos.getId();
        if (masterChronos.getSubStage() != null && !masterChronos.getSubStage().isEmpty()) {
            this.name = masterChronos.getStage();
        } else {
            this.name = masterChronos.getStage();
        }
        this.age = masterChronos.getMa();
        this.isRelative = false;
        this.isMaster = true;
    }
}
