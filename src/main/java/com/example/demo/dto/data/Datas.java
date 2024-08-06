package com.example.demo.dto.data;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@ApiModel(value = "资料实体类",description = "资料的相关属性")
@Data
@Validated
public class Datas {

    private int id;
    @Size(max = 20,message = "资料名称长度不能超过20位")
    private String dataName;

    private String typeId;
    private String typeName;
    private String dataDesc;
    private String dataStatus;
    private String uploadName;
    private String createName;
    private LocalDateTime createTime;
    private String updateName;
    private LocalDateTime updateTime;

}
