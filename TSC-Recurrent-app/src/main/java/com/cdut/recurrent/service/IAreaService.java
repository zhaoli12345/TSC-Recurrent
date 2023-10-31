package com.cdut.recurrent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdut.current.entity.Area;
import com.cdut.current.vo.AreaVO;

import java.util.List;

public interface IAreaService extends IService<Area> {
    List<AreaVO> buildAreaTree();
}
