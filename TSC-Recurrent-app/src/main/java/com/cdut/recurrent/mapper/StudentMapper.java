package com.cdut.recurrent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdut.current.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
