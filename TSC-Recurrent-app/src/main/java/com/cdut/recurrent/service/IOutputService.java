package com.cdut.recurrent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.entity.Output;

import java.util.List;

public interface IOutputService  extends IService<Output> {
    /**
     * 根据output表id查询计算年龄
     * @param id  output_id
     * @return 年龄值
     */
    float calculateAgeById(Long id,IOutputService outputService);

    List<Output> findRelativeById(Long id);

    List<Output> findAllRelativeById(Long id, IOutputService outputService);

    List<Output> findRelativeOutputById(Long id);

    List<MasterChronos> findRelativeMasterById(Long id);
}
