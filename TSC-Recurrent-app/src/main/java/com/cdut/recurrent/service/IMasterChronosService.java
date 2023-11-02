package com.cdut.recurrent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdut.current.entity.MasterChronos;

import java.util.List;

/**
 * ClassName:IMasterChronosService
 * Package:com.cdut.recurrent.service
 * Description:
 *
 * @Author 余笙
 * @Create 2023/10/31 17:05
 * @Version 1.0
 */
public interface IMasterChronosService extends IService<MasterChronos> {
    Float ageById(Long id);

    List<MasterChronos> getByIds(List<Long> ids);
}
