package com.trs.ibook.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Title: 参数异常类
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 北京拓尔思信息技术股份有限公司(TRS)
 * Project: trs-metadata
 * Author: Norman
 * Create Time: 2017-09-27 17:55
 */
public class IBookParamException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public IBookParamException() {
        this.httpStatus = HttpStatus.OK;
    }

    public IBookParamException(String message) {
        super(message);
        this.httpStatus = HttpStatus.OK;

    }

    public IBookParamException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
