package com.rainsoul.gateway.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultCodeEnum getByCode(int codeval){
        for(ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()){
            if(resultCodeEnum.code == codeval){
                return resultCodeEnum;
            }
        }
        return null;
    }
}
