package com.example.demo.mybatis.mapper;

import com.example.demo.dto.user.Role;
import com.example.demo.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HrMapper {
    /**
     * 通过用户名查询用户信息
     * @param s 用户名
     * @return 用户信息
     */
    User loadUserByUsername(@Param("username") String s);
    /**
     * 通过用户名查询用户权限信息
     * @param s 用户名
     * @return 用户权限信息
     */
    List<Role> loadUserRoleByUsername(@Param("username") String s);

    /**
     * 查询所有的角色
     * @return 角色集合
     */
    List<Role> queryRoles();

}
