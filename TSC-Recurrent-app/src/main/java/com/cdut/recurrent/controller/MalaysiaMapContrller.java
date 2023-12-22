package com.cdut.recurrent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.Area;
import com.cdut.current.entity.MalaysiaMap;
import com.cdut.current.entity.Output;
import com.cdut.current.exception.AppException;
import com.cdut.current.vo.AreaVO;
import com.cdut.current.vo.LocationVO;
import com.cdut.recurrent.service.IAreaService;
import com.cdut.recurrent.service.IMalaysiaMapService;
import com.cdut.recurrent.service.IOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IOutputService outputService;

    @Autowired
    private OutputController outputController;

    @GetMapping("/map/{mapName}")
    private ServiceResult<List<LocationVO>> showMap(@PathVariable String mapName) {
        QueryWrapper<MalaysiaMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("map_name", mapName);

        List<MalaysiaMap> maplist = malaysiaMapService.list(queryWrapper);
        if (maplist != null && !maplist.isEmpty()) {
            List<LocationVO> locationList = new ArrayList<>();

            for (MalaysiaMap malaysiaMap : maplist) {
                LocationVO locationVO = new LocationVO();
                locationVO.setLon(malaysiaMap.getLon());
                locationVO.setLat(malaysiaMap.getLat());

                long areaId = malaysiaMap.getAreaId();

                QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
                areaQueryWrapper.eq("id", areaId);
                Area area = areaService.getOne(areaQueryWrapper);

                if (area != null) {
                    locationVO.setAreaName(area.getAreaName());
                } else {
                    locationVO.setAreaName("Unknown Area");
                }
                locationList.add(locationVO);
            }
            return ServiceResult.success(locationList);

        }
        throw new AppException("Map not found!");
    }

    /**
     * 查找地区名字和图片路径
     *
     * @param mapName
     * @param age
     * @return
     */

    @GetMapping("/map")
    private ServiceResult<List<LocationVO>> showMap(@RequestParam(required = false) String mapName, @RequestParam(required = false) Integer age) {
        QueryWrapper<MalaysiaMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("map_name", mapName);

        List<MalaysiaMap> maplist = malaysiaMapService.list(queryWrapper);
        if (maplist != null && !maplist.isEmpty()) {
            List<LocationVO> locationList = new ArrayList<>();

            for (MalaysiaMap malaysiaMap : maplist) {
                LocationVO locationVO = new LocationVO();
                locationVO.setLon(malaysiaMap.getLon());
                locationVO.setLat(malaysiaMap.getLat());

                long areaId = malaysiaMap.getAreaId();

                QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
                areaQueryWrapper.eq("id", areaId);
                Area area = areaService.getOne(areaQueryWrapper);

                if (area != null) {
                    locationVO.setAreaName(area.getAreaName());

                    QueryWrapper<Output> outputQueryWrapper = new QueryWrapper<>();
                    outputQueryWrapper.eq("area_id", areaId);
                    List<Output> outputList = outputService.list(outputQueryWrapper);

                    for (Output output : outputList) {
                        ServiceResult<Float> calculateAgeResult = outputController.calculateAgeById(output.getId());
                        Float result = calculateAgeResult.getResult();
                        output.setAge(result);
                    }
                    if (age != null) {
                        List<Output> outputs = outputList.stream().filter(output -> output.getAge() > age).sorted().toList();
                        if (!outputs.isEmpty()) {
                            Output op = outputs.get(0);
                            String lithologyPattern = op.getLithologyPattern().replaceAll("\\s", "_");
                            String imgPath = "static/img/" + lithologyPattern + ".jpg";
                            locationVO.setImagePath(imgPath);
                        }
                    }
                } else {
                    locationVO.setAreaName("Unknown Area");
                }
                locationList.add(locationVO);
            }

            return ServiceResult.success(locationList);
        }
        throw new AppException("Map not found!");
    }

    @GetMapping("/getPath")
    private ServiceResult<List<LocationVO>> getPath(
            @RequestParam List<String> areaNames,
            @RequestParam  Double  start,
            @RequestParam  Double  end
    ) {
        List<LocationVO> result = new ArrayList<>();

        for (String areaName : areaNames) {
            // 查询area表中的id
            QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
            areaQueryWrapper.eq("area_name", areaName);
            Area area = areaService.getOne(areaQueryWrapper);

            if (area == null) {
                // 处理找不到area的情况
                //return ServiceResult.error("Area not found for the given name: " + areaName);
            }

            // 根据area_id在output表中查询记录
            QueryWrapper<Output> outputQueryWrapper = new QueryWrapper<>();
            outputQueryWrapper.eq("area_id", area.getId());
            List<Output> outputList = outputService.list(outputQueryWrapper);

            for (Output output : outputList) {
                // 根据outputId计算年龄
                ServiceResult<Float> calculateAgeResult = outputController.calculateAgeById(output.getId());
                if (calculateAgeResult.getResult()!=null) {
                    Float age = calculateAgeResult.getResult();

                    // 筛选符合start和end范围内的年龄
                    if (age >= start && age <= end) {
                        // 在数据表output中获取对应年龄的lithology_pattern
                        String lithologyPattern = output.getLithologyPattern().replaceAll("\\s","");

                        // 构造图片路径
                        String imgPath = "static/img/" + lithologyPattern + ".jpg";

                        // 构造LocationVO对象
                        LocationVO locationVO = new LocationVO();
                        locationVO.setAreaName(areaName);
                        locationVO.setAge(age);  // 如果需要存储年龄，可以在LocationVO中添加对应的字段
                        locationVO.setImagePath(imgPath);

                        // 添加到结果列表
                        result.add(locationVO);
                    }
                } else {
                    // 处理年龄计算失败的情况
                    // return ServiceResult.error("Failed to calculate age for output ID: " + output.getId());
                }
            }
        }

        // 按年龄从小到大排序
        result.sort(Comparator.comparingDouble(LocationVO::getAge));

        return ServiceResult.success(result);
    }
}












