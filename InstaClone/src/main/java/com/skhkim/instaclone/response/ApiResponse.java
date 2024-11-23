package com.skhkim.instaclone.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ApiResponse<T> {
    // API 응답 결과
    private boolean result;

    // API 응답 코드
    private Integer status;

    // API 메세지
    private String message;

    // API 데이터
    private T data;

    // OK
    public static <T> ResponseEntity<ApiResponse<T>> OK() {
        return new ResponseEntity<>(
                (ApiResponse<T>) ApiResponse.builder()
                        .result(true)
                        .status(HttpStatus.OK.value())
                        .message("성공")
                        .build(),
                HttpStatus.OK);
    }
    public static <T> ResponseEntity<ApiResponse<T>> OK(T data) {
        return new ResponseEntity<>(
                (ApiResponse<T>) ApiResponse.builder()
                        .result(true)
                        .status(HttpStatus.OK.value())
                        .message("성공")
                        .data(data)
                        .build(),
                HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ERROR(HttpStatus status, String message) {
        return new ResponseEntity<>(
                (ApiResponse<T>) ApiResponse.builder()
                        .result(false)
                        .status(status.value())
                        .message(message)
                        .build(),
                HttpStatus.OK);
    }
}
