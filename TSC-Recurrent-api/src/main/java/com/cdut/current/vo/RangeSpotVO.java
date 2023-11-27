package com.cdut.current.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RangeSpotVO{
    private SpotVO top;
    private SpotVO bottom;

    private List<RangeSpotVO> children = Collections.emptyList();

    private Label label;
}
