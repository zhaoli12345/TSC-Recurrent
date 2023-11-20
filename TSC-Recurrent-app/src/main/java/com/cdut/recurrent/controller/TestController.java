package com.cdut.recurrent.controller;

import com.cdut.current.common.ServiceResult;
import com.cdut.current.entity.Output;
import com.cdut.current.entity.Student;
import com.cdut.recurrent.service.IStudentService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 测试 controller
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private OutputController outputController;

    @GetMapping("/getErrorOutputIds")
    public ServiceResult<List<Long>> getErrorOutputIds() {
        List<Long> ids = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        Map<Long, Float> excelMap = getExcelList();
        for (Map.Entry<Long, Float> entry : excelMap.entrySet()) {
            try {
                ServiceResult<Float> floatServiceResult = outputController.calculateAgeById(entry.getKey());
                Float age = floatServiceResult.getResult();
                float x = age - entry.getValue();
                System.out.println(x);
                if (Math.abs(x) < 1) {
                    ids.add(entry.getKey());
                    values.add(x);
                }
            }catch (Exception e){
                System.out.println(entry.getKey());
                e.printStackTrace();
            }

        }

        return ServiceResult.success(ids);
    }

    private Map<Long, Float> getExcelList() {
        Map<Long, Float> excelMap = new HashMap<>();
        String excelFilePath = "D:\\资料\\项目\\TSC\\test.xlsx";

        try (FileInputStream inputStream = new FileInputStream(excelFilePath)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            // 迭代行
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                Cell cell1 = row.getCell(0);
                double id = cell1.getNumericCellValue();

                Cell cell2 = row.getCell(1);
                double age = cell2.getNumericCellValue();
                excelMap.put((long) id, (float) age);

                System.out.println(); // 换行处理下一行数据
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelMap;
    }
}