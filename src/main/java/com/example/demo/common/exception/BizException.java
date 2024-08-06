package com.example.demo.common.exception;

import com.example.demo.common.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException {
    private static final long SerialVersionUID = 1L;
    private String code;
    private String message;
    private ResponseCodeEnum responseCodeEnum;

    public BizException(ResponseCodeEnum responseCodeEnum){
        super(responseCodeEnum.getCode());
        this.code = responseCodeEnum.getCode();
        this.responseCodeEnum = responseCodeEnum;
        this.message = responseCodeEnum.getMessage();
    }

    public BizException(String message){
        this.message = message;
    }
}
