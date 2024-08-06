package com.example.demo.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.TestLong;
import com.example.demo.dto.cost.CostCalculationRequest;
import com.example.demo.service.CostCalculationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "费用计算的相关接口")
@RestController
@RequestMapping(path = "/api/costCalculation",produces = "application/json;charset=utf-8")
@Slf4j
@Validated
public class CostCalculationController {

    @Autowired
    private CostCalculationService costCalculationService;

    @ApiOperation(value = "根据文件读取进行费用计算，并输出到本地文件")
    @PostMapping("downloadExcel")
    public void downloadExcel(
            @RequestParam("name") String name,
            HttpServletResponse response,
            @RequestParam("file") MultipartFile file) throws Exception {
        costCalculationService.downloadExcel(name,response,file);
    }

    @ApiOperation(value = "根据文件读取进行费用计算，并输出到本地文件")
    @PostMapping("test")
    public TestLong testLong() {
        TestLong testLong  = new TestLong();

        testLong.setA("1663356170414981122");
        testLong.setB(1663356170);
        testLong.setIod(1663356170414981122l);

        String s = JSON.toJSONString(testLong);
        System.out.println(s);

        JSONObject jsonObject = JSON.parseObject(s);

        TestLong testLong2 = JSON.parseObject(s, TestLong.class);
        TestLong testLong1 = new TestLong();
        testLong1.setIod(jsonObject.getLong("iod"));
        System.out.println(testLong1);
        System.out.println(testLong2);
        return testLong2;
    }

}
