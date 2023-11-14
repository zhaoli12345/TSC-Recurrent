package com.cdut.recurrent.controller;

import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.MasterChronos;
import com.cdut.recurrent.service.IMasterChronosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

//    @RequestMapping(value = "/getDate",method = RequestMethod.GET)
//    public List<MasterChronos> getData(@RequestBody MasterChronos requestData){
//        String selectedData1 = requestData.getSelectedData1();
//        String selectedData2 = requestData.getSelectedData2();
//
//        List<MasterChronos> relatedDate = masterChronosService.findAll(selectedData1,selectedData2);
//
//        //执行业务逻辑以查找两个数据之间的其他数据
//        relatedDate = relatedDate.stream()
//                .filter(item -> (item.getMa().equals(selectedData1) || item.getMa().equals(selectedData2)) &&
//                        !item.getMa().equals(selectedData1) && !item.getMa().equals(selectedData2))
//                .collect(Collectors.toList());
//        return relatedDate;
//    }

    @GetMapping("/allEntities")
    public ServiceResult<List<MasterChronos>> findAll(){
        return ServiceResult.success(masterChronosService.findAll());
    }
}
