//package com.example.demo.config;
//
//import com.example.demo.security.MyAccessDecisionManager;
//import com.example.demo.security.MyFilterInvocationSecurityMetadataSource;
//import com.example.demo.service.HrService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)//全局
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private HrService hrService;//得到登录用户拥有的权限
//
//    @Autowired
//    private MyFilterInvocationSecurityMetadataSource filterMetadataSource; //权限过滤器（当前访问所需要的权限）
//    @Autowired
//    private MyAccessDecisionManager myAccessDecisionManager;//权限决策器，根据用户拥有的权限和访问需要的权限
//
//    /**
//     * 配置userDetails的数据源
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(hrService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    /**
//     *
//     * @param web
//     */
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/login")
//                // 给 swagger 放行；不需要权限能访问的资源
//                .antMatchers("/swagger-ui.html","/login.html", "/swagger-resources/**","/images/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/configuration/security");
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setSecurityMetadataSource(filterMetadataSource);
//                        o.setAccessDecisionManager(myAccessDecisionManager);
//                        return o;
//                    }
//                })
//                .and()
//                //在没有登陆时，页面会跳转的链接
//                .formLogin().loginPage("/login.html").loginProcessingUrl("/swagger-ui.html")
//                .usernameParameter("username").passwordParameter("password")
////                .failureHandler(new MyAuthenticationFailureHandler())
////                .successHandler(new MyAuthenticationSuccessHandler())
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
////                .logoutSuccessHandler(new MyLogoutSuccessHandler())
//                .permitAll()
//                .and().csrf().disable();
////                .exceptionHandling().accessDeniedHandler(deniedHandler);
//    }
//
//
//}
