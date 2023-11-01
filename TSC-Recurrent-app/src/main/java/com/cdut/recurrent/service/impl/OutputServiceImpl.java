package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.Output;
import com.cdut.current.exception.AppException;
import com.cdut.recurrent.mapper.OutputMapper;
import com.cdut.recurrent.service.IOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OutputServiceImpl extends ServiceImpl<OutputMapper, Output> implements IOutputService {

    public static String regex = "\\$\\{([^}]+)}";

    @Autowired
    private OutputMapper outputMapper;

    @Override
    public float calculateAgeByFormula(String formula) {
        return 0;
    }

    @Override
    public float calculateAgeById(Long id) {
        Output output = outputMapper.selectById(id);
        if (output == null) {
            throw new AppException("当前id不存在");
        }
        //绝对数据，直接返回
        if (!output.getIsRelative()) {
            return Float.parseFloat(output.getMaFormula());
        }

        //相对数据，判断公式中id的来源（主表/本表）
        if (output.getIsPrimary()){

        }
        //栈
        Stack<Long> stack=new Stack<>();
        //id-age的映射关系
        HashMap<Long, Float> ageMap = new HashMap<>();
        String formula = output.getMaFormula();


        return 0;
    }

    public float calculateAge(Map<Long, Float> agesMap,String formula){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formula);

        // 替换匹配项
        StringBuilder expression = new StringBuilder();
        while (matcher.find()) {
            String match = matcher.group(1);
            Float age = agesMap.get(Long.parseLong(match));
            matcher.appendReplacement(expression,age.toString());
        }
        matcher.appendTail(expression);
        return 0;

    }

    //master表
    private Map<Long, Float> getAgesByFormula(String formula){
        return new HashMap<>();
    }
}
