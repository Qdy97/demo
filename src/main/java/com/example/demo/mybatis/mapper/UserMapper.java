package com.example.demo.mybatis.mapper;

import com.example.demo.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    /**
     * 新增用户
     * @param user
     */
    void add(@Param("user") User user);

    /**
     * 根据用户名查找数据库密码
     * @param loginId 用户名
     * @return 数据库中的用户名
     */
    User queryUserByUsername(@Param("loginId") String loginId);

    /**
     * 根据用户名查询有多少数据
     * @param loginId 用户名
     * @return 该用户名有多少
     */
    int selectCountsByLoginId(@Param("loginId") String loginId);

    /**
     * 查询用户列表
     * @param user 用户信息条件
     * @return 用户列表
     */
    List<User> selectUsers(@Param("user") User user);

    /**
     * 根据ID删除数据
     * @param id
     * @return
     */
    void deleteById(@Param("id") Integer id);
}
