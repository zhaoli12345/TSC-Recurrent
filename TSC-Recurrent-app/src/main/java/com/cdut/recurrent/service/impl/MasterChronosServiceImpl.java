package com.cdut.recurrent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdut.current.entity.MasterChronos;
import com.cdut.current.vo.LimitVO;
import com.cdut.current.vo.MasterChartVO;
import com.cdut.recurrent.mapper.MasterChronosMapper;
import com.cdut.recurrent.service.IMasterChronosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName:MasterChronosServiceImpl
 * Package:com.cdut.recurrent.service.impl
 * Description:
 *
 * @Author 余笙
 * @Create 2023/10/31 17:06
 * @Version 1.0
 */
@Service
public class MasterChronosServiceImpl extends ServiceImpl<MasterChronosMapper, MasterChronos> implements IMasterChronosService {
    @Autowired
    private MasterChronosMapper masterChronosMapper;

    private List<String> mapMasterChronoToList(MasterChronos masterChrono) {
        return Arrays.asList(
                masterChrono.getId().toString(),
                masterChrono.getMa().toString(),
                masterChrono.getPeriod(),
                masterChrono.getEpoch(),
                masterChrono.getStage()
        );
    }

    private LimitVO mapToLimitVO(MasterChronos masterChrono) {
        LimitVO limitVO = new LimitVO();
        limitVO.setId(masterChrono.getId());
        limitVO.setStage(masterChrono.getStage());
        limitVO.setMa(masterChrono.getMa());
        return limitVO;
    }

    //生成随机的RGB颜色
    private String generateRandomColor(Random random){
        int r = random.nextInt(128) + 128;
        int g = random.nextInt(158) + 128;
        int b = random.nextInt(128) + 128;
        return String.format("#%02X%02X%02X",r,g,b);
    }

    @Override
    public Float ageById(Long id) {
        MasterChronos masterChronos = masterChronosMapper.selectById(id);
        return masterChronos.getMa();
    }

    @Override
    public List<MasterChronos> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            // 如果 ids 为空集合，可以根据业务需求返回一个空列表，或者抛出异常，具体根据业务场景决定
            return Collections.emptyList(); // 返回一个空列表
            // 或者抛出异常
            // throw new IllegalArgumentException("ids 参数不能为空");
        }
        return masterChronosMapper.selectBatchIds(ids);
    }


    @Override
    public List<List<String>> findAll() {
        return masterChronosMapper.selectList(null).stream()
                .map(this::mapMasterChronoToList)
                .collect(Collectors.toList());
    }

    @Override
    public List<LimitVO> getLimited() {
        return masterChronosMapper.selectList(null).stream()
                .map(this::mapToLimitVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MasterChartVO> getChartValue() {
        List<MasterChartVO> chartlist1 = new ArrayList<>();
        List<MasterChartVO> chartlist2 = new ArrayList<>();
        List<MasterChartVO> chartlist3 = new ArrayList<>();
        List<MasterChartVO> allchartlist = new ArrayList<>();
        List<MasterChronos> masterChronos = masterChronosMapper.selectList(null);
        String previousData1 = null;
        String previousData2 = null;
        String previousData3 = null;
        MasterChartVO periodChartVO = null;
        MasterChartVO epochChartVO = null;
        MasterChartVO stageChartVO = null;
        Random random = new Random();
        for (MasterChronos masterChronos1 : masterChronos){
            if (previousData1 == null || !previousData1.equals(masterChronos1.getPeriod())) {
                if (periodChartVO != null) {
                    chartlist1.add(periodChartVO);
                }
                periodChartVO = new MasterChartVO();
                // 添加第一个元素，使用 masterChronos1.getPeriod()
                periodChartVO.setName(masterChronos1.getPeriod());
                periodChartVO.setData(new ArrayList<>(List.of(masterChronos1.getMa(), "null", "null")));
                periodChartVO.setColor(generateRandomColor(random));
            }else{
                //更新 masterChartVO 的 data 值
                periodChartVO.getData().set(0,masterChronos1.getMa());
            }
            previousData1 = masterChronos1.getPeriod();
        }
        if (periodChartVO != null) {
            chartlist1.add(periodChartVO);
        }
        for (MasterChronos masterChronos1 : masterChronos){
            if (previousData2 == null || !previousData2.equals(masterChronos1.getEpoch())) {
                if (epochChartVO != null){
                    chartlist2.add(epochChartVO);
                }
                // 添加第二个元素，使用 GetEpoch 的属性值
                epochChartVO = new MasterChartVO();
                epochChartVO.setName(masterChronos1.getEpoch()); // 使用相应的属性，可能需要根据你的代码进行修改
                epochChartVO.setData(new ArrayList<>(List.of("null", masterChronos1.getMa(), "null")));
                epochChartVO.setColor(generateRandomColor(random));
            }else{
                //更新 masterChartVO 的 data 值
                epochChartVO.getData().set(1,masterChronos1.getMa());
            }
            previousData2 = masterChronos1.getEpoch();
        }
        if (epochChartVO != null){
            chartlist2.add(epochChartVO);
        }
        for (MasterChronos masterChronos1 : masterChronos){
            if (previousData3 == null || !previousData3.equals(masterChronos1.getStage())) {
                if (stageChartVO != null){
                    chartlist3.add(stageChartVO);
                }
                // 添加第三个元素，使用 getStage 的属性值
                stageChartVO = new MasterChartVO();
                stageChartVO.setName(masterChronos1.getStage()); // 使用相应的属性，可能需要根据你的代码进行修改
                stageChartVO.setData(new ArrayList<>(List.of("null", "null", masterChronos1.getMa())));
                stageChartVO.setColor(generateRandomColor(random));
            }else{
                stageChartVO.getData().set(2,masterChronos1.getMa());
            }
            previousData3 = masterChronos1.getStage();
        }
        // 处理最后一个元素
        if (stageChartVO != null){
            chartlist3.add(stageChartVO);
        }
        ChangeGetChart(chartlist1,0);
        ChangeGetChart(chartlist2,1);
        ChangeGetChart(chartlist3,2);
        allchartlist.addAll(chartlist1);
        allchartlist.addAll(chartlist2);
        allchartlist.addAll(chartlist3);
        return allchartlist;
    }

    public void ChangeGetChart(List<MasterChartVO> chartlist,int k){
        List<List<Object>> preData = pullChartData(chartlist);
        if (preData.size() < chartlist.size() - 1) {
            System.out.println("preData 的大小不足以匹配 chartlist");
            return ; // 或者添加适当的错误处理
        }
        for (int i = 1; i < chartlist.size(); i++) {
            MasterChartVO currentChart = chartlist.get(i);
            List<Object> currentData = currentChart.getData();
            List<Object> preValue = preData.get(i - 1);
            if (!preValue.isEmpty()){
                currentData.set(k, (float)currentData.get(k) - (float) preValue.get(k));
            }else {
                System.out.println("preValue 为空");
            }
        }
    }

    public List<List<Object>> pullChartData(List<MasterChartVO> chartlist){
        List<List<Object>> extractedData = new ArrayList<>();
        for (MasterChartVO chartVO : chartlist){
            List<Object> data = new ArrayList<>(chartVO.getData());
            extractedData.add(data);
        }
        return extractedData;
    }


}
