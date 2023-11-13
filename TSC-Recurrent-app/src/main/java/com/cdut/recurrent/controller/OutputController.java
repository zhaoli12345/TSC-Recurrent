package com.cdut.recurrent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.entity.Output;
import com.cdut.current.exception.AppException;
import com.cdut.current.vo.SpotVO;
import com.cdut.recurrent.service.IOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Output> outputs = outputService.list(queryWrapper);
        setAges(outputs);

        Collections.sort(outputs);
        return ServiceResult.success(outputs);
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ServiceResult<Output> findById(@PathVariable Long id) {
        Output output = getOutputById(id);
        return ServiceResult.success(output);
    }

    @RequestMapping(value = "/findByIds", method = RequestMethod.POST)
    public ServiceResult<List<Output>> findByIds(@RequestBody List<Long> ids) {
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

    @RequestMapping(value = "/findOutputAndRelativeById", method = RequestMethod.GET)
    public ServiceResult<SpotVO> findOutputAndRelativeById(Long id) {
        Output output = getOutputById(id);
        SpotVO spotVO = new SpotVO(output);
        //获取相关联数据
        setAllRelative(spotVO);
        return ServiceResult.success(spotVO);
    }

    private void setAllRelative(SpotVO spotVO) {
        //已经是主表了 or 绝对数据
        if (spotVO.getIsMaster() || !spotVO.getIsRelative()) {
            return;
        }

        //output表，相对数据
        Output output = getOutputById(spotVO.getId());
        List<SpotVO> spotVOList = new ArrayList<>();
        if (output.getIsPrimary()) {
            List<Output> outputs = outputService.findRelativeOutputById(output.getId());
            if (outputs!=null && !outputs.isEmpty()){
                setAges(outputs);
                for (Output op : outputs) {
                    SpotVO sp = new SpotVO(op);
                    setAllRelative(sp);
                    spotVOList.add(sp);
                }
            }
        } else {
            List<MasterChronos> masterChronos = outputService.findRelativeMasterById(output.getId());
            if (masterChronos!=null && !masterChronos.isEmpty()){
                for (MasterChronos mc : masterChronos) {
                    SpotVO sp = new SpotVO(mc);
                    spotVOList.add(sp);
                }
            }
        }
        spotVO.setChildren(spotVOList);
    }

    private Output getOutputById(Long id) {
        Output output = outputService.getById(id);
        if (output == null) {
            throw new AppException("当前id不存在:" + id);
        }
        float age = outputService.calculateAgeById(id, outputService);
        output.setAge(age);
        return output;
    }

    //给output赋予年龄
    private void setAges(List<Output> outputs) {
        for (Output output : outputs) {
            float age = outputService.calculateAgeById(output.getId(), outputService);
            output.setAge(age);
        }
    }
}
