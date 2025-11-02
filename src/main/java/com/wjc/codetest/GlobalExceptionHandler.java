package com.wjc.codetest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(value = {"com.wjc.codetest.product.controller"})
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> runTimeException(Exception e) {
        log.error("status :: {}, errorType :: {}, errorCause :: {}",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "runtimeException",
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
// 문제: 클라이언트에 어떤 오류인지 알 수 있는 정보 제공하지 않음
// 원인: ResponseEntity body를 비워서 반환
// 개선안: 에러 메시지를 포함한 표준 Error Response DTO 반환
// 선택 근거: API 사용자에게 명확한 오류 정보 제공, 디버깅 용이성 증가

// 문제: 모든 RuntimeException을 동일하게 500 처리 → 세부 오류 구분 불가
// 원인: 커스텀 예외 처리 미구현
// 개선안: ProductNotFoundException 등 도메인별 커스텀 예외 정의 후 적절한 HTTP 상태 반환
// 선택 근거: REST API 표준에 따른 상태 코드 관리, 클라이언트 처리 용이
