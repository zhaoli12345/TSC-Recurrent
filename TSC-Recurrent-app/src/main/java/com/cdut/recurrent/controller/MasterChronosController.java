package com.cdut.recurrent.controller;

import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.Area;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.entity.Output;
import com.cdut.current.vo.LimitVO;
import com.cdut.current.vo.MasterChartVO;
import com.cdut.current.vo.SvgVO;
import com.cdut.recurrent.mapper.AreaMapper;
import com.cdut.recurrent.mapper.OutputMapper;
import com.cdut.recurrent.service.IMasterChronosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @Autowired
    private OutputMapper outputMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private OutputController outputController;

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
    public ServiceResult<List<List<String>>> findAll(){
        return ServiceResult.success(masterChronosService.findAll());
    }

    @GetMapping("/getma")
    public ServiceResult<List<LimitVO>> getLimited(){
        return ServiceResult.success(masterChronosService.getLimited());
    }

    @GetMapping("/getChartValue")
    public ServiceResult<List<MasterChartVO>> getChartValue(){
        return ServiceResult.success(masterChronosService.getChartValue());
    }

    @GetMapping("/getSVGvalue")
    public List<SvgVO> getSVGheight(){
        List<SvgVO> svgVOList = new ArrayList<>();
        List<Output> outputs = outputMapper.selectList(null);
        List<SvgVO> unsortedSvgVOList = new ArrayList<>();
        Float previousResult = null; // 初始化前一个结果
        Float faciesLevelSum = 0f;
        Float previousFaciesLevel = null;
        Long previousAreaId = null;
        for (Output output : outputs){
            SvgVO svgVO = new SvgVO();
            svgVO.setAreaId(output.getAreaId());
            svgVO.setLithologyPattern(output.getLithologyPattern());
            svgVO.setFaciesLevel(output.getFaciesLevel());
            ServiceResult<Float> calculateAgeResult=outputController.calculateAgeById(output.getId());
            Float currentResult = calculateAgeResult.getResult();
            svgVO.setFormula(currentResult);
            String imgPath = output.getLithologyPattern().replaceAll("\\s", "_");
            svgVO.setImgPath("static/img/"+ imgPath+".jpg");
            unsortedSvgVOList.add(svgVO);
        }
        // 根据 areaId 相同时，以 setFormula 属性升序方式对 svgVOList 进行排序
        Collections.sort(unsortedSvgVOList, new Comparator<SvgVO>() {
            @Override
            public int compare(SvgVO svg1, SvgVO svg2) {
                // 首先比较 areaId
                int areaIdComparison = svg1.getAreaId().compareTo(svg2.getAreaId());

                // 如果 areaId 相同，再比较 setFormula
                if (areaIdComparison == 0) {
                    // 这里假设 setFormula 是 Float 类型，如果是其他类型，请相应地调整比较逻辑
                    return Float.compare(svg1.getFormula(), svg2.getFormula());
                } else {
                    return areaIdComparison;
                }
            }
        });
        for (SvgVO svgVO : unsortedSvgVOList) {
            if (previousResult != null && !svgVO.getAreaId().equals(previousAreaId)) {
                // 当前一个areaId和现在areaId不同时，将previousFormula初始化
                previousResult = null;
                faciesLevelSum = 0f; // 重置 FaciesLevel 的和
                previousFaciesLevel = null;
            }
            if (previousResult != null) {
                svgVO.setHeight(svgVO.getFormula() - previousResult);
                svgVO.setFLheight(svgVO.getFormula() - previousResult);
            }
            previousResult = svgVO.getFormula();
            previousAreaId = svgVO.getAreaId();
            svgVOList.add(svgVO);
        }
        // 循环结束后，对 svgVOList 进行筛选，删除 height 为 0 或者 null 的项
        svgVOList = svgVOList.stream()
                .filter(svgVO -> svgVO.getHeight() != null && !svgVO.getHeight().equals(0f))
                .collect(Collectors.toList());
        return svgVOList;
    }

    @GetMapping("/getDiffSvgValue")
    public ServiceResult<Map<String,List<SvgVO>>> getDiffSvgValue(){
        Map<String, List<SvgVO>> listMap = new HashMap<>();
        Map<Long,String> areaIdAndName = new HashMap<>();
        List<SvgVO> svgVOList = new ArrayList<>(getSVGheight());
        List<Area> areaList = areaMapper.selectList(null);
        for (Area area : areaList){
            areaIdAndName.put(area.getId(),area.getAreaName());
        }
        for (SvgVO svGheight : svgVOList){
            Long areaId = svGheight.getAreaId();
            if (listMap.containsKey(areaIdAndName.get(areaId))){
                listMap.get(areaIdAndName.get(areaId)).add(svGheight);
            }else {
                List<SvgVO> newList = new ArrayList<>();
                newList.add(svGheight);
                listMap.put(areaIdAndName.get(areaId),newList);
            }
        }

        // 自定义按键首字母排序的Comparator
        Comparator<String> keyComparator = Comparator.comparing(String::toLowerCase);
        // 使用自定义Comparator创建TreeMap
        Map<String, List<SvgVO>> sortedListMap = new TreeMap<>(keyComparator);
        sortedListMap.putAll(listMap);

        return ServiceResult.success(sortedListMap);
    }
}
