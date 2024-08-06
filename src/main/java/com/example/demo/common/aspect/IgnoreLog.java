package com.example.demo.common.aspect;

import java.lang.annotation.*;

/**
 * 忽略日志记录
 *
 * @author 75412
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface IgnoreLog {

    /**
     * 是否忽略请求 默认true
     */
    boolean ignoreRequest() default true;

    /**
     * 是否忽略响应 默认true
     */
    boolean ignoreResponse() default true;

}
