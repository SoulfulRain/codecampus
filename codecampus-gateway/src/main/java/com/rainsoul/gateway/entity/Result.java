package com.rainsoul.gateway.entity;

import com.rainsoul.gateway.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class Result<T> {

    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    public static Result ok() {
        Result result = new Result<>();
        result.setSuccess(true);
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        return result;
    }

    public static <T> Result ok(T data) {
        Result result = new Result<>();
        result.setSuccess(true);
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setData(data);
        return result;
    }

    public static <T> Result fail() {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(ResultCodeEnum.FAIL.getMessage());
        result.setCode(ResultCodeEnum.FAIL.getCode());
        return result;
    }

    public static <T> Result fail(T data) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(ResultCodeEnum.FAIL.getMessage());
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setData(data);
        return result;
    }

    public static <T> Result fail(Integer code, String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

}
