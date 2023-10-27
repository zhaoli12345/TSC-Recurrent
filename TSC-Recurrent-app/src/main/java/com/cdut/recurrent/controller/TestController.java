package com.cdut.recurrent.controller;

import com.cdut.current.entity.Student;
import com.cdut.recurrent.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 controller
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private IStudentService studentService;

    @GetMapping("/allStudent")
    public Student allStudent() {
        return studentService.getById(1);
    }
}