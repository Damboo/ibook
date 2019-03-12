package com.trs.ibook.core.exception;

import com.season.common.JsonUtil;
import com.season.core.error.ErrorConstants;
import com.season.core.error.ErrorVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Title:异常翻译器
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: season
 * Author: Vincent
 * Create Time:2017/5/12 11:52
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class IBookExceptionTranslator {

    private final Logger logger = LoggerFactory.getLogger(IBookExceptionTranslator.class);

    @ExceptionHandler(IBookParamException.class)
    public ResponseEntity<ErrorVM> processHshopParamException(IBookParamException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(httpStatus);
        ErrorVM errorVM = new ErrorVM(ErrorConstants.ERR_VALIDATION, ex.getMessage());

        return builder.body(errorVM);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        logger.warn(JsonUtil.toJSONString(fieldErrors));
        return processFieldErrors(fieldErrors);
    }

    private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
        ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION);
        if (fieldErrors.size() > 0) {
            dto = new ErrorVM(ErrorConstants.ERR_VALIDATION, fieldErrors.get(0).getDefaultMessage());
        }
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }


}
