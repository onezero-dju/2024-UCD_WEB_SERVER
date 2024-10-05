package com.ucd.keynote.common.exception;

import com.ucd.keynote.common.dto.NoDataApiResponseDTO;
import com.ucd.keynote.domain.user.exception.EmailAlreadyExistsException;
import com.ucd.keynote.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 이메일 중복 처리
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<NoDataApiResponseDTO> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(409)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 사용자 존재하지 않음 예외 처리
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<NoDataApiResponseDTO> handleUserNotFound(UserNotFoundException ex) {
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(404)  // 404 Not Found
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
