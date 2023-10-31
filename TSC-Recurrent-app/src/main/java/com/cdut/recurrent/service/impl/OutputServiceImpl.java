package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.Output;
import com.cdut.recurrent.mapper.OutputMapper;
import com.cdut.recurrent.service.IOutputService;
import org.springframework.stereotype.Service;

@Service
public class OutputServiceImpl  extends ServiceImpl<OutputMapper, Output> implements IOutputService {
    @Override
    public float calculateAgeByFormula(String formula) {
        return 0;
    }

    @Override
    public float calculateAgeById(Long id) {
        return 0;
    }
}
