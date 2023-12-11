package com.cdut.recurrent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.vo.LimitVO;
import com.cdut.current.vo.MasterChartVO;

import java.util.List;
import java.util.Map;

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

    List<List<String>> findAll();

    List<LimitVO> getLimited();

    String getNameAndColor(String name);

    List<MasterChartVO> getChartValue();
}
