//package com.example.demo.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.Iterator;
//
///**
// * @author 75412
// * @date 2020/11/19
// * @desc 有了访问当前路径所需要的权限，可以决定当前的访问是否可以通过
// */
//@Slf4j
//@Component
//public class MyAccessDecisionManager implements AccessDecisionManager{
//
//
//    /**
//     * @Description: 取当前用户的权限与这次请求的这个url需要的权限作对比，决定是否放行
//     * authentication 包含了当前的用户信息，包括拥有的权限,即之前UserDetailsService登录时候存储的用户对象
//     * object 就是FilterInvocation对象，可以得到request等web资源。
//     * configAttributes 是本次访问需要的权限。即上一步的 MyFilterInvocationSecurityMetadataSource 中查询核对得到的权限列表
//     * @param authentication
//     * @param o
//     * @param collection
//     * @throws AccessDeniedException
//     * @throws InsufficientAuthenticationException
//     */
//    @Override
//    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
//        Iterator<ConfigAttribute> iterator = collection.iterator();
//        while (iterator.hasNext()){
//            if (authentication == null){
//                throw new AccessDeniedException("当前访问没有权限");
//            }
//            ConfigAttribute ca = iterator.next();
//            //当前登录需要的权限
//            String role = ca.getAttribute();
//            //如果匹配到了写死的权限则直接返回
//            if ("all".equals(role)){
//                return;
//            }
//            //当前用户所具有的权限
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(role)) {
//                    return;
//                }
//            }
//        }
//        throw new AccessDeniedException("权限不足!");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute configAttribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//}
