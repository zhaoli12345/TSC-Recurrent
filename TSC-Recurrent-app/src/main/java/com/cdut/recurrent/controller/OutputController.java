package com.cdut.recurrent.controller;

import com.cdut.current.common.ServiceResult;
import com.cdut.recurrent.service.IOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("output")
public class OutputController {
    @Autowired
    private IOutputService outputService;

    /**
     * 根据output表id查询计算年龄
     *
     * @param id id
     * @return 地质年龄
     */
    @RequestMapping(value = "/calculateAgeById/{id}", method = RequestMethod.GET)
    public ServiceResult<Float> calculateAgeById(@PathVariable Long id) {
        return ServiceResult.success(outputService.calculateAgeById(id,outputService));
    }
}
