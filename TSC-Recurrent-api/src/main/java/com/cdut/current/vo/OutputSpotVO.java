package com.cdut.current.vo;

import com.cdut.current.entity.Output;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputSpotVO extends SpotVO{
    private Long areaId;

    private String lithologyPattern;

    private String faciesLevel;

    private Boolean isRelative;

    private Boolean isPrimary;

    private String maFormula;   //年龄公式

    private String descr;

    private String combinedComments;

    private String lithology;

    private String calibrationComments;

    private Float age;

    public OutputSpotVO(Output output){
        super(output);

        this.areaId=output.getAreaId();
        this.lithologyPattern=output.getLithologyPattern();
        this.faciesLevel=output.getFaciesLevel();
        this.isRelative=output.getIsRelative();
        this.isPrimary=output.getIsPrimary();
        this.maFormula=output.getMaFormula();
        this.descr=output.getDescr();
        this.combinedComments=output.getCombinedComments();
        this.lithology=output.getLithology();
        this.calibrationComments=output.getCalibrationComments();
        this.age=output.getAge();
    }
}
