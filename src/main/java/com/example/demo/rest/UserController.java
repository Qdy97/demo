package com.example.demo.rest;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.common.group.add;
import com.example.demo.dto.user.ResponseUser;
import com.example.demo.dto.user.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.ApiResponseUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 75412
 * <p>
 * Demo增删改查示例接口
 */

@Api(tags = "用户登录以及新增接口")
@RestController
@RequestMapping(path = "/api/user", produces = "application/json;charset=utf-8")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "新增用户")
    @PostMapping("")
    public ApiResponseEntity add(
            @ApiParam(value = "用户实体类") @RequestBody @Validated({add.class}) User user
    ) {
        return ApiResponseUtils.success(userService.add(user));
    }

    @ApiOperation(value = "用户登录校验")
    @PostMapping("/login")
    @SentinelResource("login")
    public ApiResponseEntity<ResponseUser> login(
            @ApiParam(value = "用户实体类") @RequestBody User user) {
        return userService.login(user);
    }

    @ApiOperation(value = "用户列表")
    @PostMapping("/list")
    public ApiResponseEntity<PageInfo<User>> list(
            @ApiParam(value = "用户实体类") @RequestBody(required = false) User user,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNo) {
        return userService.list(user, pageNo, pageSize);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/delete")
    public ApiResponseEntity delete(
            @RequestParam Integer id
    ) {
        userService.delete(id);
        return ApiResponseUtils.success();
    }

}
