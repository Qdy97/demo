package com.example.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * @author 75412
 */
@Data
@ApiModel(value = "ApiResponseEntity")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseEntity<T> {

    /**
     * 业务响应码
     */
    @ApiModelProperty(value = "响应状态码",example = "0")
    private String code;

    /**
     * 业务响应消息
     */
    @ApiModelProperty(value = "业务响应消息",example = "异常时为exception中的message")
    private String message;

    /**
     * 业务响应数据
     */
    @ApiModelProperty(value = "业务响应数据",example = "{}")
    @Nullable
    private T data;

    public ApiResponseEntity(String code,String message){
        this.code = code;
        this.message = message;
    }
}
