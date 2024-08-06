package com.example.demo.common.enums;

import com.example.demo.common.Contants;
import lombok.Getter;

/**
 * @author 75412
 * 返回值信息枚举类
 */
@Getter
public enum ResponseCodeEnum {

    SUCCESS("0","200","请求执行成功"),
    FAIL("1","9999","请求执行失败"),
    VALID_EXCEPTION("1","9998","数据校验异常");

    private String code;
    private String messageId;
    private String message;

    ResponseCodeEnum(String code,String message,String messageId){
        this.code = code;
        this.message = message;
        this.messageId = messageId;
    }

    public String getFullMessage(){
        return Contants.EXCEPTION_PREFIX
                + this.getMessageId()
                + Contants.EXCEPTION_SPLIT
                + this.getMessage();
    }

}
