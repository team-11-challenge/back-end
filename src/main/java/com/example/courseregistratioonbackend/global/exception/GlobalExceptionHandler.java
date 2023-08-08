package com.example.courseregistratioonbackend.global.exception;

import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ex)
 * <pre>
 * &#64;ExceptionHandler(UserException.class)
 * public ApiResponse<?> handleUserException(UserException e) {
 *      return ResponseUtils.error(e.getErrorCode());
 * }
 * </pre>
 */
@Slf4j(topic = "global exception handler")
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.info(e.getMessage());
        return ResponseUtils.error(e.getMessage());
    }

    @ExceptionHandler(GlobalException.class)
    public ApiResponse<?> handleGlobalException(GlobalException e) {
        return ResponseUtils.error(e.getErrorCode());
    }

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ApiResponse<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
		log.info(e.getMessage());
		return ResponseUtils.error(e.getMessage());
	}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> validationExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(), error.getDefaultMessage()
                ));
        return ResponseUtils.error(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseUtils.error(e.getMessage());
    }
}
