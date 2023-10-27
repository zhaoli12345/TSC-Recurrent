package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.Student;
import com.cdut.recurrent.mapper.StudentMapper;
import com.cdut.recurrent.service.IStudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
}
