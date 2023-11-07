package com.cdut.recurrent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.Output;
import com.cdut.current.exception.AppException;
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
        return ServiceResult.success(outputService.calculateAgeById(id, outputService));
    }

    @RequestMapping(value = "/findsByAreaId/{areaId}", method = RequestMethod.GET)
    public ServiceResult<List<Output>> findsByAreaId(@PathVariable Long areaId) {
        QueryWrapper<Output> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("area_id", areaId);
        return ServiceResult.success(outputService.list(queryWrapper));
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ServiceResult<Output> findById(@PathVariable Long id) {
        Output output = outputService.getById(id);
        if (output == null) {
            throw new AppException("当前id不存在");
        }
        float age = outputService.calculateAgeById(id, outputService);
        output.setAge(age);
        return ServiceResult.success(output);
    }

    @RequestMapping(value = "/findByIds", method = RequestMethod.POST)
    public ServiceResult<List<Output>> findByIds(@RequestBody  List<Long> ids) {
        List<Output> outputs = outputService.listByIds(ids);
        setAges(outputs);

        return ServiceResult.success(outputs);
    }

    /**
     * 根据当前id查询关联id信息
     *
     * @param id output id
     * @return 关联地质信息（包含age）
     */
    @RequestMapping(value = "/findRelativeById", method = RequestMethod.GET)
    public ServiceResult<List<Output>> findRelativeById(Long id) {
        List<Output> outputs = outputService.findRelativeById(id);

        setAges(outputs);
        return ServiceResult.success(outputs);
    }

    //给output赋予年龄
    private void setAges(List<Output> outputs) {
        for (Output output : outputs) {
            float age = outputService.calculateAgeById(output.getId(), outputService);
            output.setAge(age);
        }
    }
}
