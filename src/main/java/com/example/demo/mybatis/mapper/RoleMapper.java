package com.example.demo.mybatis.mapper;

import com.example.demo.dto.user.Role;
import com.example.demo.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleMapper {

    /**
     * 根据用户信息查询角色
     * @param owner 用户
     * @return 角色
     */
    Role queryRoleByUser(@Param("owner") User owner);

    /**
     * 对用户和权限关联表进行新增
     * @param loginId 用户信息
     * @param roleCode 角色编码
     */
    void add(@Param("loginId") String loginId,@Param("roleCode") String roleCode);

    /**
     * 根据ID删除权限
     * @param id
     * @return
     */
    int deleteRoleById(@Param("id") Integer id);
}
