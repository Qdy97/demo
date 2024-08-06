package com.example.demo.common.aspect;

import com.example.demo.common.dto.ApiResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 请求参数打印
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 切点切入的位置
     */
    @Pointcut("execution(* com.example.demo.rest..*.*(..))")
    public void controllerPoint(){}

    /**
     * 前置获取需要记录的参数
     * @param joinPoint 切点
     */
    @Before("controllerPoint()")
    public void before(JoinPoint joinPoint){
        //检查是否忽略
        if (ignoreRequest(joinPoint)){
            return;
        }
        log.info("开始记录");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null){
            log.info("查询不到request信息");
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        log.info("请求url={},请求方式={},请求ip={},请求目标类={},请求目标方法={}",request.getRequestURL(),request.getMethod(),
                request.getRemoteAddr(),joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();
        if (args != null){
            for (int i = 0; i < args.length; i++) {
                log.info("方法入参:args{}={}",i + 1,args[i]);
            }
        }
    }

    @AfterReturning(pointcut = "controllerPoint()",returning = "apiResponseEntity")
    public void doAfterReturning(JoinPoint joinPoint, ApiResponseEntity apiResponseEntity){
        //检查是否忽略
        if(ignoreResponse(joinPoint)){
            return;
        }
        log.info("方法出参:{}",apiResponseEntity);
        log.info("记录结束");
    }

    /**
     * 忽略求点的入参请求
     * @param joinPoint 切点
     * @return 是否忽略
     */
    private boolean ignoreRequest(JoinPoint joinPoint){
        IgnoreLog annotation = getIgnoreAnnotation(joinPoint);
        return annotation != null && annotation.ignoreRequest();
    }

    /**
     * 忽略求点的出参请求
     * @param joinPoint 切点
     * @return 是否忽略
     */
    private boolean ignoreResponse(JoinPoint joinPoint){
        IgnoreLog annotation = getIgnoreAnnotation(joinPoint);
        return annotation != null && annotation.ignoreResponse();
    }

    /**
     * 获取忽略日志注解
     * @param joinPoint 切点
     * @return 忽略日志注解
     */
    private IgnoreLog getIgnoreAnnotation(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return AnnotationUtils.findAnnotation(method,IgnoreLog.class);
    }
}
