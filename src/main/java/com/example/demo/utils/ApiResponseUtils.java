package com.example.demo.utils;

import com.example.demo.common.dto.ApiResponseEntity;
import com.example.demo.common.enums.ResponseCodeEnum;

/**
 * @author 75412
 */
public class ApiResponseUtils {

    public static <T> ApiResponseEntity<T>  success(){
        return successResponse();
    }

    public static <T> ApiResponseEntity<T> success(T data){
        ApiResponseEntity<T> re = successResponse();
        re.setData(data);
        return re;
    }

    public static <T> ApiResponseEntity<T> fail(){
        return failResponse();
    }

    public static <T> ApiResponseEntity fail(T data){
        ApiResponseEntity<T> re = failResponse();
        re.setData(data);
        return re;
    }

    public static <T> ApiResponseEntity fail(String message){
        ApiResponseEntity<T> re = failResponse();
        re.setMessage(message);
        return re;
    }

    public static <T> ApiResponseEntity fail(String code,String message){
        return new ApiResponseEntity(code,message);
    }

    public static <T> ApiResponseEntity fail(ResponseCodeEnum responseCodeEnum){
        return new ApiResponseEntity(responseCodeEnum.getCode(),responseCodeEnum.getMessage());
    }

    public static <T> ApiResponseEntity fail(ResponseCodeEnum responseCodeEnum,T data){
        return new ApiResponseEntity(responseCodeEnum.getCode(),responseCodeEnum.getMessage(),data);
    }

    private static <T> ApiResponseEntity<T> successResponse() {
        return new ApiResponseEntity<>(
                ResponseCodeEnum.SUCCESS.getCode(),ResponseCodeEnum.SUCCESS.getMessage());
    }

    private static <T> ApiResponseEntity<T> failResponse() {
        return new ApiResponseEntity<>(
                ResponseCodeEnum.FAIL.getCode(),ResponseCodeEnum.FAIL.getMessage());
    }

    public static <T> ApiResponseEntity<T> resultConvert(boolean result){
        if (result){
            return success();
        } else {
            return fail();
        }
    }

    public static <T> ApiResponseEntity resultConvert(Object result){
        if (result == null){
            return fail();
        } else {
            return success(result);
        }
    }
}
