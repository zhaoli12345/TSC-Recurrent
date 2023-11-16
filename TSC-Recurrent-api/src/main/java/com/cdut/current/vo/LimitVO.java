package com.cdut.current.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:LimitVO
 * Package:com.cdut.current.vo
 * Description:
 *
 * @Author 余笙
 * @Create 2023/11/16 14:55
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitVO {
    private Long id;

    private String stage;

    private Float ma;


}
