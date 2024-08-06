//package com.example.demo.service;
//
//import com.example.demo.dto.user.Role;
//import com.example.demo.dto.user.User;
//import com.example.demo.mybatis.mapper.HrMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Transactional
//public class HrService implements UserDetailsService {
//
//    @Autowired
//    private HrMapper hrMapper;
//
//    /**
//     * 从数据库中查出账号密码
//     * @param s 帐号
//     * @return 帐号以及密码
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = hrMapper.loadUserByUsername(s);
//        List<GrantedAuthority> authorities = createAuthorities(s);
//        UserDetails ud = new org.springframework.security.core.userdetails.User(s,user.getPassword(),authorities);
//        if (ud == null){
//            throw new UsernameNotFoundException("该用户名无法找到："+s);
//        }
//        return ud;
//    }
//
//    /**
//     * 通过用户名查找该用户下所有角色
//     * @param username 用户名
//     * @return 用户名对应的角色集合
//     */
//    private List<GrantedAuthority> createAuthorities(String username){
//        List<GrantedAuthority> lists = new ArrayList<>();
//        List<Role> roles = hrMapper.loadUserRoleByUsername(username);
//        for (Role role : roles) {
//            lists.add(new SimpleGrantedAuthority(role.getRoleCode()));
//        }
//        return lists;
//    }
//}
