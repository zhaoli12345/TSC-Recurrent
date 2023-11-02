package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.Area;
import com.cdut.current.vo.AreaVO;
import com.cdut.recurrent.mapper.AreaMapper;
import com.cdut.recurrent.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<AreaVO> buildAreaTree() {
        // 默认对所有数据进行查询
        List<AreaVO> areaVOS = areaMapper.selectList(null).stream().map(Area::toAreaVO).toList();
        Map<Long, List<AreaVO>> areaVOMaps = areaVOS.stream().collect(Collectors.groupingBy(AreaVO::getParentId));

        return getRootNode(areaVOMaps);
    }

    private List<AreaVO> getRootNode(Map<Long, List<AreaVO>> areaVOMaps) {
        List<AreaVO> rootAreas = areaVOMaps.get(0L);
        if (!CollectionUtils.isEmpty(rootAreas)) {
            for (AreaVO rootArea : rootAreas) {
                buildTree(rootArea, areaVOMaps);
            }
        }
        return rootAreas;
    }

    private void buildTree(AreaVO parentArea, Map<Long, List<AreaVO>> areaVOMaps) {
        List<AreaVO> childrenAreaVOs = areaVOMaps.get(parentArea.getId());
        if (!CollectionUtils.isEmpty(childrenAreaVOs)) {
            parentArea.setLeaf(false);
            parentArea.setChildrenArea(childrenAreaVOs);
            for (AreaVO childrenAreaVO : childrenAreaVOs) {
                buildTree(childrenAreaVO, areaVOMaps);
            }
        }
    }
}
