package com.example.demo.rest;

import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.dto.user.User;
import com.example.demo.service.RoleService;
import com.example.demo.utils.ApiResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 75412
 * <p>
 * 权限相关接口
 */

@Api(tags = "权限相关接口")
@RestController
@RequestMapping(path = "/api/role", produces = "application/json;charset=utf-8")
@Slf4j
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询权限")
    @PostMapping("")
    public ApiResponseEntity<Boolean> add(
            @ApiParam(value = "用户实体类") @RequestBody User user
    ) {
        System.out.println();
//        userService.add(user);
        return ApiResponseUtils.success();
    }

}
