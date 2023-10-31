package com.cdut.current.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdut.current.vo.AreaVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("area")
public class Area {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "area_name")
    private String areaName;

    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "descr")
    private String descr;

    public AreaVO toAreaVO() {
        return new AreaVO(id, areaName, parentId, descr, true, null);
    }
}
