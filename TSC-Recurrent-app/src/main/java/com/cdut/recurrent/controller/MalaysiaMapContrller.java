package com.cdut.recurrent.controller;

import com.cdut.current.common.ServiceResult;
import com.cdut.current.vo.AreaVO;
import com.cdut.recurrent.service.IAreaService;
import com.cdut.recurrent.service.IMalaysiaMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName:MalaysiaMapContrller
 * Package:com.cdut.recurrent.controller
 * Description:
 *
 * @Author 余笙
 * @Create 2023/10/31 16:43
 * @Version 1.0
 */
@RestController
@RequestMapping("malaysiamap")
public class MalaysiaMapContrller {
    @Autowired
    private IMalaysiaMapService malaysiaMapService;

}
