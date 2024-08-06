package com.example.demo.dto.user;

import com.example.demo.common.group.Edit;
import com.example.demo.common.group.Search;
import com.example.demo.common.group.add;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 75412
 * 用户实体类
 */
@ApiModel(value = "User",description = "人员信息")
@Data
@Validated
public class User implements Serializable{

    @ApiModelProperty(value = "用户id")
    @NotBlank(
            groups = {Edit.class},
            message = "用户ID不能为空"
    )
    private Integer id;

    @NotBlank(message = "用户姓名不能为空",groups = {add.class})
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(max = 20,min = 5,message = "请将密码设置为5-20位",groups = {add.class})
    private String password;

    @NotBlank(message = "帐号不能为空")
    @Size(max = 20,min = 5,message = "请将帐号设置为5-20位",groups = {add.class})
    private String loginId;
    private String age;
    private String gender;
    private String userDesc;
    private LocalDateTime expiryDate;
    private String createName;
    private LocalDateTime createTime;
    private String updateName;
    private LocalDateTime updateTime;

    @NotBlank(message = "权限码不能为空",groups = {add.class})
    private String roleCode;
}
