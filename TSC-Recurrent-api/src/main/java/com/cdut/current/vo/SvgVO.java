package com.cdut.current.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName:SvgVO
 * Package:com.cdut.current.vo
 * Description:
 *
 * @Author 余笙
 * @Create 2023/12/12 21:02
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SvgVO {
    private Long areaId;

    private String lithologyPattern;

    private String faciesLevel;

    private Float formula;

    private Float height;

    private String imgPath;

    private Float FLheight;


}
