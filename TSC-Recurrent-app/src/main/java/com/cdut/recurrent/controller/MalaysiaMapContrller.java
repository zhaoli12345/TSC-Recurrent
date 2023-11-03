package com.cdut.recurrent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.Area;
import com.cdut.current.entity.MalaysiaMap;
import com.cdut.current.exception.AppException;
import com.cdut.current.vo.AreaVO;
import com.cdut.current.vo.LocationVO;
import com.cdut.recurrent.service.IAreaService;
import com.cdut.recurrent.service.IMalaysiaMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    @Autowired
    private IAreaService areaService;

    @GetMapping("/map/{mapName}")
    private ServiceResult<List<LocationVO>>showMap(@PathVariable String mapName) {
        QueryWrapper<MalaysiaMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("map_name", mapName);

        List<MalaysiaMap> maplist = malaysiaMapService.list(queryWrapper);
        if (maplist != null && !maplist.isEmpty()) {
            List<LocationVO> locationList=new ArrayList<>();

            for(MalaysiaMap malaysiaMap:maplist){
                LocationVO locationVO=new LocationVO();
                locationVO.setLon(malaysiaMap.getLon());
                locationVO.setLat(malaysiaMap.getLat());

                long areaId=malaysiaMap.getAreaId();

                QueryWrapper<Area> areaQueryWrapper=new QueryWrapper<>();
                areaQueryWrapper.eq("id",areaId);
                Area area=areaService.getOne(areaQueryWrapper);

                if(area!=null){
                    locationVO.setAreaName(area.getAreaName());
                }else {
                    locationVO.setAreaName("Unknown Area");
                }
                locationList.add(locationVO);
            }
            return ServiceResult.success(locationList);

        }
        throw new AppException("Map not found!");

    }
}







