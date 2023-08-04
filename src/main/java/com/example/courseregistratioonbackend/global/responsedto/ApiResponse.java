package com.example.courseregistratioonbackend.global.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final boolean success;
    private final int statusCode;
    private final String msg;
    private final T data;
    private final T errors;

    public ApiResponse(boolean success, int statusCode, String msg, T data, T errors) {
        this.success = success;
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
        this.errors = errors;
    }
}
