package com.example.demo.service;

import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.dto.user.ResponseUser;
import com.example.demo.dto.user.Role;
import com.example.demo.dto.user.User;
import com.example.demo.mybatis.mapper.RoleMapper;
import com.example.demo.mybatis.mapper.UserMapper;
import com.example.demo.utils.ApiResponseUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Transactional(rollbackFor = Exception.class)
    public ApiResponseEntity add(User user) {
        //查询用户名是否重复
        int counts = userMapper.selectCountsByLoginId(user.getLoginId());
        if (counts>0){
            return ApiResponseUtils.fail("用户名重复");
        }
        //校验后进行用户新增
        userMapper.add(user);
        //对用户和权限关联表进行新增
        roleMapper.add(user.getLoginId(),user.getRoleCode());
        return ApiResponseUtils.success();
    }

    /**
     * 校验
     * @param user 用户信息
     * @return 是否成功登录
     */
    public ApiResponseEntity login(User user) {
        User owner = userMapper.queryUserByUsername(user.getLoginId());
        if (owner == null){
            return ApiResponseUtils.fail("用户名不存在,请检查后再次登录");
        }
        if (owner.getPassword() == null){
            return ApiResponseUtils.fail("用户名不存在,请检查后再次登录");
        }
        if (!owner.getPassword().equals(user.getPassword())){
            return ApiResponseUtils.fail("密码错误,请检查后再次登录");
        }
        //校验成功后,查询权限信息后返回
        Role role = roleMapper.queryRoleByUser(owner);
        ResponseUser responseUser = new ResponseUser();
        responseUser.setAge(owner.getAge());
        responseUser.setUsername(owner.getUsername());
        responseUser.setUserDesc(owner.getUserDesc());
        responseUser.setUpdateTime(owner.getUpdateTime());
        responseUser.setStatus(role.getStatus());
        responseUser.setRoleName(role.getRoleName());
        responseUser.setRoleCode(role.getRoleCode());
        responseUser.setLoginId(owner.getLoginId());
        responseUser.setGender(owner.getGender());
        responseUser.setExpiryDate(owner.getExpiryDate());
        responseUser.setCreateTime(owner.getCreateTime());
        responseUser.setCreateName(owner.getCreateName());
        return ApiResponseUtils.success(responseUser);
    }


    public ApiResponseEntity<PageInfo<User>> list(User user,Integer pageNo,Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<User> users = userMapper.selectUsers(user);
        PageInfo<User> userInfo= new PageInfo<>(users);
        return ApiResponseUtils.success(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        //先删除帐号对应权限
        roleMapper.deleteRoleById(id);
        //在删除帐号信息
        userMapper.deleteById(id);
    }
}
