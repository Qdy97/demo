package com.example.demo.dto.cost;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "费用计算入参实体类",description = "费用计算的相关参数")
@Data
@Validated
public class CostCalculationRequest {
    /**
     * 读取文件路径
     */
    @NotBlank(message = "读取文件路径不能为空")
    private String uploadPath;

    /**
     * 结果下载路径
     */
    @NotBlank(message = "结果下载路径不能为空")
    private String downloadPath;

    /**
     * 需要计算的列名，多个用英文逗号隔开
     */
    @NotBlank(message = "需要计算的列名不能为空")
    private String lines;

    /**
     * 存放结果的列名，多个用英文逗号隔开
     */
    @NotBlank(message = "存放结果的列名不能为空")
    private String resultLines;
}
