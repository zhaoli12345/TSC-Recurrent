package com.cdut.recurrent.controller;

import com.cdut.current.common.ServiceResult;
import com.cdut.current.vo.AreaVO;
import com.cdut.recurrent.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("area")
public class AreaController {
    @Autowired
    private IAreaService areaService;

    @GetMapping("/buildAreaTree")
    public ServiceResult<List<AreaVO>> buildAreaTree() {
        return ServiceResult.success(areaService.buildAreaTree());
    }
}
