package com.example.demo.common.exception;

import com.example.demo.common.enums.ResponseCodeEnum;
import com.example.demo.utils.ApiResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author 75412
 * 统一异常业务处理
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ExceptionHandler{

    private ResponseEntity<Object> buildResponseEntity(Object entity){
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * 业务自定义异常
     * @param ex 异常
     * @param request 请求
     * @return 返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({BizException.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(BizException ex, WebRequest request){
        if (ex.getResponseCodeEnum() != null){
            return buildResponseEntity(ApiResponseUtils.fail(ex.getResponseCodeEnum()));
        }
        return buildResponseEntity(ApiResponseUtils.fail(ex.getMessage()));
    }

    /**
     * 兜底异常处理
     * @param ex 异常
     * @param request 请求
     * @return 返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception ex,WebRequest request){
        log.error("内部异常:{}",ex);
        return buildResponseEntity(ApiResponseUtils.fail());
    }

    /**
     * hibernate validation 校验异常处理
     * @param ex 异常
     * @param request 请求
     * @return 返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex,WebRequest request){
        BindingResult bindingResult = ex.getBindingResult();
        String bindingExceptionMessage = getBindingExceptionMessage(bindingResult);
        return buildResponseEntity(ApiResponseUtils.fail(ResponseCodeEnum.VALID_EXCEPTION.getCode(),bindingExceptionMessage));
    }

    /**
     * spring 数据绑定异常处理
     * @param ex 异常
     * @param request 请求
     * @return 返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({BindException.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(BindException ex,WebRequest request){
        String bindingExceptionMessage = getBindingExceptionMessage(ex);
        return buildResponseEntity(ApiResponseUtils.fail(ResponseCodeEnum.VALID_EXCEPTION.getCode(),bindingExceptionMessage));
    }

    /**
     * spring validation 异常处理
     * @param ex 异常
     * @param request 请求
     * @return 返回值
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(ConstraintViolationException ex,WebRequest request){
        return buildResponseEntity(ApiResponseUtils.fail());
    }

    /**
     * 获取BindingResult中的提示信息
     * @param bindingResult  异常对象
     * @return 提示信息
     */
    private String getBindingExceptionMessage(BindingResult bindingResult){
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError allError : allErrors) {
            sb.append(",").append(allError.getDefaultMessage());
        }
        return ResponseCodeEnum.VALID_EXCEPTION.getFullMessage() + sb.toString();
    }
}
