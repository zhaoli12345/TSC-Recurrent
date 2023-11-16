package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.entity.Output;
import com.cdut.current.exception.AppException;
import com.cdut.current.util.PatternUtil;
import com.cdut.recurrent.mapper.MasterChronosMapper;
import com.cdut.recurrent.service.IMasterChronosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


//    @Autowired
//    public MasterChronosServiceImpl(MasterChronosMapper masterChronosMapper){
//        this.masterChronosMapper = masterChronosMapper;
//    }

    @Override
    public List<List<String>> findAll() {
        List<List<String>> allList=new ArrayList<>();

        List<MasterChronos> masterChronos = masterChronosMapper.selectList(null);
        for (MasterChronos masterChrono : masterChronos) {
            List<String> list=new ArrayList<>();
            list.add(masterChrono.getId().toString());
            list.add(masterChrono.getMa().toString());
            list.add(masterChrono.getPeriod());
            list.add(masterChrono.getEpoch());
            list.add(masterChrono.getStage());
            allList.add(list);
        }
        return allList;
    }

    @Override
    public List<String> getma() {
        List<String> list = new ArrayList<>();
        List<MasterChronos> masterChronos = masterChronosMapper.selectList(null);
        for (MasterChronos masterChronos1 : masterChronos){

            list.add(masterChronos1.getStage());
            list.add(masterChronos1.getMa().toString());
        }
        return list;
    }


}
