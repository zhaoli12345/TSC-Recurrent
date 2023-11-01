package com.cdut.recurrent.controller;

import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.MasterChronos;
import com.cdut.recurrent.service.IMasterChronosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:MasterChronosController
 * Package:com.cdut.recurrent.controller
 * Description:
 *
 * @Author 余笙
 * @Create 2023/10/31 17:08
 * @Version 1.0
 */
@RestController
@RequestMapping("masterchronos")
public class MasterChronosController {
    @Autowired
    private IMasterChronosService masterChronosService;

    @RequestMapping(value = "/selectById/{id}", method = RequestMethod.GET)
    public ServiceResult<MasterChronos> selectById(@PathVariable Long id) {
        return ServiceResult.success(masterChronosService.getById(id));
    }

    @RequestMapping(value = "/ageById/{id}", method = RequestMethod.GET)
    public ServiceResult<Float> ageById(@PathVariable Long id) {
        return ServiceResult.success(masterChronosService.ageById(id));
    }
}
