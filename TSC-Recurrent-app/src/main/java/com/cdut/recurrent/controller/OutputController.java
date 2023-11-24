package com.cdut.recurrent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.Area;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.entity.Output;
import com.cdut.current.exception.AppException;
import com.cdut.current.vo.*;
import com.cdut.recurrent.service.IAreaService;
import com.cdut.recurrent.service.IOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("output")
public class OutputController {
    @Autowired
    private IOutputService outputService;

    @Autowired
    private IAreaService areaService;

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
        List<Output> filterList = new ArrayList<>();
        for (Output output : outputs) {
            String lithologyPattern = output.getLithologyPattern();
            if (!"top".equalsIgnoreCase(lithologyPattern) && !"gap".equalsIgnoreCase(lithologyPattern)) {
                filterList.add(output);
            }
        }
        setAges(filterList);

        Collections.sort(filterList);
        return ServiceResult.success(filterList);
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

    @RequestMapping(value = "/findOutputAndRelativeById/{id}", method = RequestMethod.GET)
    public ServiceResult<SpotVO> findOutputAndRelativeById(@PathVariable Long id) {
        Output output = getOutputById(id);
        SpotVO parentSpotVO = new OutputSpotVO(output);
        //获取相关联数据
        setAllRelative(parentSpotVO);
        return ServiceResult.success(parentSpotVO);
    }

    @RequestMapping(value = "/findRangeSpotById/{id}", method = RequestMethod.GET)
    public ServiceResult<RangeSpotVO> findRangeSpotById(@PathVariable Long id) {
        RangeSpotVO rangeSpotVO = new RangeSpotVO();

        // bottomSpotVO
        Output output = getOutputById(id);
        SpotVO bottomSpotVO = new OutputSpotVO(output);
        rangeSpotVO.setBottom(bottomSpotVO);

        // topSpotVO
        QueryWrapper<Output> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("area_id", output.getAreaId());
        List<Output> outputs = outputService.list(queryWrapper);
        setAges(outputs);
        outputs = outputs.stream().filter(output1 -> output1.getAge() < output.getAge()).toList();
        setAges(outputs);
        SpotVO topSpotVO = null;
        for (int i = 0; i < outputs.size(); i++) {
            if (topSpotVO == null) {
                topSpotVO = new OutputSpotVO(outputs.get(i));
            } else {
                if (topSpotVO.getAge() <= outputs.get(i).getAge()) {
                    topSpotVO = new OutputSpotVO(outputs.get(i));
                }
            }
        }
        rangeSpotVO.setTop(topSpotVO);

        //获取相关联数据
        setAllRangeSpot(rangeSpotVO);
        return ServiceResult.success(rangeSpotVO);
    }

    private void setAllRangeSpot(RangeSpotVO rangeSpotVO) {
        List<RangeSpotVO> childrenVO = new ArrayList<>();
        if ((rangeSpotVO.getTop().getIsMaster() || !rangeSpotVO.getTop().getIsRelative()) && (rangeSpotVO.getBottom().getIsMaster() || !rangeSpotVO.getBottom().getIsRelative())) {
            //给叶子结点配置单独颜色
            Label label = new Label("#6E6E6E");
            rangeSpotVO.setLabel(label);
            return;
        }

        SpotVO topSpotVO = rangeSpotVO.getTop();
        setTopOrBottom(topSpotVO, childrenVO);
        SpotVO bottomSpotVO = rangeSpotVO.getBottom();
        setTopOrBottom(bottomSpotVO, childrenVO);

        if (!childrenVO.isEmpty()) {
            for (RangeSpotVO spotVO : childrenVO) {
                setAllRangeSpot(spotVO);
            }
        }

        rangeSpotVO.setChildren(childrenVO);
    }


    private void setTopOrBottom(SpotVO spotVO, List<RangeSpotVO> rangeVOs) {
        if (spotVO != null) {

            if (spotVO.getIsMaster() || !spotVO.getIsRelative()) {
                //给叶子结点配置单独颜色
                Label label = new Label("#6E6E6E");
                spotVO.setLabel(label);
                return;
            }
            //output表，相对数据
            Output output = getOutputById(spotVO.getId());

            RangeSpotVO rangeVO = new RangeSpotVO();
            if (output.getIsPrimary()) {
                List<Output> outputs = outputService.findRelativeOutputById(output.getId());
                if (outputs != null && !outputs.isEmpty()) {
                    setAges(outputs);
                    outputs.sort(Output::compareTo);//升序

                    Output top = null;
                    Output bottom = null;
                    if (outputs.size() == 2) {
                        top = outputs.get(0);  //top
                        bottom = outputs.get(1);  //bottom
                    } else {  //size=1
                        top = outputs.get(0);  //top
                        bottom = outputs.get(0);  //bottom
                    }

                    SpotVO bottomVO = new OutputSpotVO(bottom);
                    SpotVO topVO = new OutputSpotVO(top);

                    rangeVO.setTop(topVO);
                    rangeVO.setBottom(bottomVO);
                }
            } else {
                List<MasterChronos> masterChronos = outputService.findRelativeMasterById(output.getId());
                if (masterChronos != null && !masterChronos.isEmpty()) {
                    MasterChronos top = null;
                    MasterChronos bottom = null;

                    if (masterChronos.size() == 2) {
                        top = masterChronos.get(0);  //top
                        bottom = masterChronos.get(1);  //bottom
                    } else {  //size=1
                        top = masterChronos.get(0);
                        bottom = masterChronos.get(0);
                    }
                    SpotVO bottomVO = new MasterSpotVO(bottom);
                    SpotVO topVO = new MasterSpotVO(top);

                    rangeVO.setTop(topVO);
                    rangeVO.setBottom(bottomVO);
                }
            }
            rangeVOs.add(rangeVO);
        }
    }

    private void setAllRelative(SpotVO spotVO) {
        //已经是主表了 or 绝对数据
        if (spotVO.getIsMaster() || !spotVO.getIsRelative()) {
            //给叶子结点配置单独颜色
            Label label = new Label("#6E6E6E");
            spotVO.setLabel(label);
            return;
        }

        //output表，相对数据
        Output output = getOutputById(spotVO.getId());
        List<SpotVO> spotVOList = new ArrayList<>();
        if (output.getIsPrimary()) {
            List<Output> outputs = outputService.findRelativeOutputById(output.getId());
            if (outputs != null && !outputs.isEmpty()) {
                setAges(outputs);
                for (Output op : outputs) {
                    Area area = areaService.getById(op.getAreaId());
                    op.setAreaName(area.getAreaName());
                    SpotVO sp = new OutputSpotVO(op);
                    setAllRelative(sp);
                    spotVOList.add(sp);
                }
            }
        } else {
            List<MasterChronos> masterChronos = outputService.findRelativeMasterById(output.getId());
            if (masterChronos != null && !masterChronos.isEmpty()) {
                for (MasterChronos mc : masterChronos) {
                    SpotVO sp = new MasterSpotVO(mc);
                    setAllRelative(sp);
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
        Area area = areaService.getById(output.getAreaId());
        if (area != null) {
            output.setAreaName(area.getAreaName());
        }
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
