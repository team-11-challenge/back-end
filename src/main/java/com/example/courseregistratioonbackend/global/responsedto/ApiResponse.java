package com.example.courseregistratioonbackend.global.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공통 응답 폼")
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Schema(description = "성공유무")
    private final boolean success;

    @Schema(description = "HTTP 응답 코드")
    private final int statusCode;

    @Schema(description = "요약 메세지")
    private final String msg;

    @Schema(description = "요청에서 요구한 데이터")
    private final T data;

    @Schema(description = "에러 메세지")
    private final T errors;

    public ApiResponse(boolean success, int statusCode, String msg, T data, T errors) {
        this.success = success;
        this.statusCode = statusCode;
        this.msg = msg;
        this.data = data;
        this.errors = errors;
    }
}
