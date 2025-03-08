package com.kjq.common;

import com.kjq.exception.ServiceException;

/**
 * 返回工具类
 *
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(int errorCode, String message) {
        return new BaseResponse<>(errorCode, null, message);
    }

    /**
     * 抛出异常
     *
     * @param errorCode
     * @return
     */
    public static ServiceException exception(ErrorCode errorCode) {
        return new ServiceException(errorCode);
    }

}
