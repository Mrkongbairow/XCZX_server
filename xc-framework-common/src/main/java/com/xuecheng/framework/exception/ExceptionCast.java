package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 抛出异常
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        //抛出自定义异常
        throw new CustomException(resultCode);
    }
}
