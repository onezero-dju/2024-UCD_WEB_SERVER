package com.ucd.keynote.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    // DataIntegrityViolationException 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // 여기에서 상세한 로그를 남길 수도 있습니다.
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다.");
    }
}
