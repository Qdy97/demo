package com.example.demo.service;

import com.example.demo.common.exception.BizException;
import com.example.demo.dto.cost.CostCalculationRequest;
import com.example.demo.utils.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class CostCalculationService {


    public void downloadExcel(String name,HttpServletResponse response, MultipartFile file) throws Exception {



//        if (costCalculationRequest.getLines().length() != costCalculationRequest.getResultLines().length()) {
//            throw new BizException("需要计算的列数和结果列数不一致");
//        }
//        String[] resultLineArray = costCalculationRequest.getResultLines().split(",");
//        String[] lineArray = costCalculationRequest.getLines().split(",");
//        if (lineArray.length != resultLineArray.length) {
//            throw new BizException("需要计算的列数和结果列数不一致");
//        }

        //读取文件
        Map<String, List<Double>> stringListMap = ExcelUtil.readExcelLis(file);

        //写出文件
        ExcelUtil.listToExcel(stringListMap,response,name);

    }
}
