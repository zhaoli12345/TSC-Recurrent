package com.cdut.recurrent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdut.current.entity.Output;

public interface IOutputService  extends IService<Output> {
    /**
     * 根据output表id查询计算年龄
     * @param id  output_id
     * @return 年龄值
     */
    float calculateAgeById(Long id,IOutputService outputService);
}
