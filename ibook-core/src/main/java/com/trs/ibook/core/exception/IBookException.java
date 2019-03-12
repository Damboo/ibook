package com.trs.ibook.core.exception;

import org.springframework.http.HttpStatus;

/**
 * Title: 自定义异常
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 北京拓尔思信息技术股份有限公司(TRS)
 * Project: trs-metadata
 * Author: Norman
 * Create Time: 2017-09-27 17:55
 */
public class IBookException extends RuntimeException {

    private HttpStatus httpStatus;

    public IBookException(String message) {
        super(message);
    }

    public IBookException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
