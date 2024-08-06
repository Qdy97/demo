//package com.example.demo.security;
//
//import com.example.demo.dto.user.Role;
//import com.example.demo.mybatis.mapper.HrMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//
///**
// * @author 75412
// * @date 2020/11/19
// * @desc 自定义权限资源过滤器，实现动态的权限验证
// * 它的主要责任是当用户访问一个url时，对这个url进行查询，返回这个url所需要的访问权限
// * 此时因为我们不需要对url进行拦截，只需要提供各个渠道的权限即可
// */
//@Component
//@Slf4j
//public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
//
//    @Autowired
//    private HrMapper hrMapper;
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//        //获取访问的url
//        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//        //获取所有的权限
//        List<Role> roleList = hrMapper.queryRoles();
//        String[] roles = new String[roleList.size()];
//        for (int i = 0; i < roleList.size(); i++) {
//            roles[i] = roleList.get(i).getRoleCode();
//        }
//        //如果没有匹配上的则填写dianxiao
//        //如果本方法返回null的话，意味着当前这个请求不需要任何角色就能访问
//        //此处做逻辑控制，如果没有匹配上的，返回一个默认具体权限，防止漏缺资源配置
//        if (roles.length == 0){
//            return SecurityConfig.createList("all");
//        }
//        return SecurityConfig.createList(roles);
//    }
//
//    /**
//     * @desc 此处方法如果做了实现，返回了定义的权限资源列表，
//     * Spring Security会在启动时校验每个ConfigAttribute是否配置正确，
//     * 如果不需要校验，这里实现方法，方法体直接返回null即可。
//     * @return
//     */
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    /**
//     * 方法返回类对象是否支持校验
//     * @param aClass
//     * @return
//     */
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//}
