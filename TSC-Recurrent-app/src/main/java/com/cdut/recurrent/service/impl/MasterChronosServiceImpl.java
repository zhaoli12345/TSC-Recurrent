package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.MasterChronos;
import com.cdut.recurrent.mapper.MasterChronosMapper;
import com.cdut.recurrent.service.IMasterChronosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:MasterChronosServiceImpl
 * Package:com.cdut.recurrent.service.impl
 * Description:
 *
 * @Author 余笙
 * @Create 2023/10/31 17:06
 * @Version 1.0
 */
@Service
public class MasterChronosServiceImpl extends ServiceImpl<MasterChronosMapper, MasterChronos> implements IMasterChronosService {
    @Autowired
    private MasterChronosMapper masterChronosMapper;

    @Override
    public Float ageById(Long id) {
        MasterChronos masterChronos = masterChronosMapper.selectById(id);
        return masterChronos.getMa();
    }

    @Override
    public List<MasterChronos> getByIds(List<Long> ids) {
        return masterChronosMapper.selectBatchIds(ids);
    }
}
