package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.entity.Output;
import com.cdut.current.exception.AppException;
import com.cdut.current.util.PatternUtil;
import com.cdut.recurrent.mapper.MasterChronosMapper;
import com.cdut.recurrent.mapper.OutputMapper;
import com.cdut.recurrent.service.IMasterChronosService;
import com.cdut.recurrent.service.IOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutputServiceImpl extends ServiceImpl<OutputMapper, Output> implements IOutputService {

    public static String regex = "\\$\\{([^}]+)}";

    @Autowired
    private IMasterChronosService masterChronosService;

    @Autowired
    private OutputMapper outputMapper;

    @Autowired
    private MasterChronosMapper masterChronosMapper;

    @Override
    public float calculateAgeById(Long id, IOutputService outputService) {
        Output output = outputMapper.selectById(id);
        if (output == null) {
            throw new AppException("当前id不存在");
        }
        //绝对数据，直接返回
        if (!output.getIsRelative()) {
            return Float.parseFloat(output.getMaFormula());
        }

        //相对数据，主表
        if (!output.getIsPrimary()) {
            //主表
            List<Long> ids = PatternUtil.getIdsFromFormula(output.getMaFormula());
            List<MasterChronos> masterChronos = masterChronosService.getByIds(ids);
            Map<Long, Float> ageMap = masterChronos.stream().collect(Collectors.toMap(MasterChronos::getId, MasterChronos::getMa, (k1, k2) -> k1));
            return PatternUtil.calculateAge(ageMap, output.getMaFormula());
        }

        //相对数据，本表
        //id-age的映射关系
        HashMap<Long, Float> ageMap = new HashMap<>();
        String formula = output.getMaFormula();
        List<Long> ids = PatternUtil.getIdsFromFormula(formula);
        List<Output> outputs = outputMapper.selectBatchIds(ids);
        if (!CollectionUtils.isEmpty(outputs)) {
            for (Output op : outputs) {
                //递归操作
                float age = outputService.calculateAgeById(op.getId(), outputService);
                ageMap.put(op.getId(), age);
            }
        }
        return PatternUtil.calculateAge(ageMap, formula);
    }

    @Override
    public List<Output> findRelativeById(Long id) {
        Output output = outputMapper.selectById(id);
        if (output == null) {
            throw new AppException("当前output id不存在：" + id);
        }

        List<Long> ids = PatternUtil.getIdsFromFormula(output.getMaFormula());
        return outputMapper.selectBatchIds(ids);
    }

    @Override
    public List<Output> findRelativeOutputById(Long id) {
        Output output = outputMapper.selectById(id);
        if (output == null) {
            throw new AppException("当前output id不存在：" + id);
        }

        List<Long> ids = PatternUtil.getIdsFromFormula(output.getMaFormula());
        return outputMapper.selectBatchIds(ids);
    }

    @Override
    public List<MasterChronos> findRelativeMasterById(Long id) {
        Output output = outputMapper.selectById(id);
        if (output == null) {
            throw new AppException("当前master id不存在：" + id);
        }

        List<Long> ids = PatternUtil.getIdsFromFormula(output.getMaFormula());

        return masterChronosMapper.selectBatchIds(ids);
    }

    @Override
    public List<Output> findAllRelativeById(Long id, IOutputService outputService) {
        return null;
    }
}
