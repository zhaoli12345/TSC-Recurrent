package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.Area;
import com.cdut.current.entity.Student;
import com.cdut.recurrent.mapper.AreaMapper;
import com.cdut.recurrent.mapper.StudentMapper;
import com.cdut.recurrent.service.IAreaService;
import com.cdut.recurrent.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {
    @Autowired
    private AreaMapper areaMapper;
}
