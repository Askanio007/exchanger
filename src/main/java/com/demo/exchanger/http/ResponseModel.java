package com.demo.exchanger.http;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseModel<T> {

    private boolean success;
    private T data;
    private String error;

    public static <T> ResponseModel<T> ok(T data) {
        return new ResponseModel<>(true, data, null);
    }

    public static <T> ResponseModel<T> failed(String error) {
        return new ResponseModel<>(false, null, error);
    }
}
