package com.ruoyi.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.ErrorCode;
import com.ruoyi.common.exception.enums.GlobalErrorCodeConstants;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通用返回
 *
 * @param <T> 数据泛型
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * 错误码
     *
     * @see ErrorCode#getCode()
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误提示，用户可阅读
     *
     * @see ErrorCode#getMsg() ()
     */
    private String msg;

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     *
     * 因为 A 方法返回的 CommonResult 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T> 返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> CommonResult<T> error(CommonResult<?> result) {
        return error(result.getCode(), result.getMsg());
    }

    public static <T> CommonResult<T> error(Integer code, String message) {
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code), "code 必须是错误的！");
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }


    public static <T> CommonResult<T> success() {
        return CommonResult.success("操作成功");
    }

    public static <T> CommonResult<T> success(String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.code = HttpStatus.SUCCESS;
        result.msg = msg;
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        return CommonResult.success("操作成功",data);
    }

    public static <T> CommonResult<T> success(String msg, T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = HttpStatus.SUCCESS;
        result.msg = msg;
        result.data = data;
        return result;
    }


    public static <T> CommonResult<T> error(String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.code = HttpStatus.BAD_REQUEST;
        result.msg = msg;
        return result;
    }

    public static <T> CommonResult<T> error() {
        return CommonResult.error("操作失败");
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, GlobalErrorCodeConstants.SUCCESS.getCode());
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isSuccess() {
        return isSuccess(code);
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isError() {
        return !isSuccess();
    }
}
