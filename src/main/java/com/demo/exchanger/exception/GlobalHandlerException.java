package com.demo.exchanger.exception;

import com.demo.exchanger.http.ResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ResponseModel.failed(ex.getMessage()), new HttpHeaders(), HttpStatus.OK, request);
    }
}
