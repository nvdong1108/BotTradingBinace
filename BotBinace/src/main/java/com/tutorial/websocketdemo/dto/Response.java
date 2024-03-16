package com.tutorial.websocketdemo.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Response<T> implements Serializable {

    private String message;
    private Integer status;
    private Integer total;
    private T data;

    public static <T> Response<T> build(HttpStatus httpStatus, T data, Integer total) {
        Response<T> response = new Response<T>();
        response.setMessage(httpStatus.name());
        response.setStatus(httpStatus.value());
        response.setData(data);
        response.setTotal(total);
        return response;
    }

}
