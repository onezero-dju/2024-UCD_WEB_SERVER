package com.ucd.keynote.common.exception;

import com.ucd.keynote.common.dto.ApiResponseDTO;
import com.ucd.keynote.common.dto.NoDataApiResponseDTO;
import com.ucd.keynote.domain.organization.exception.DuplicateOrganizationNameException;
import com.ucd.keynote.domain.organization.exception.InsufficientPermissionException;
import com.ucd.keynote.domain.organization.exception.InvalidOrganizationDataException;
import com.ucd.keynote.domain.user.exception.EmailAlreadyExistsException;
import com.ucd.keynote.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 유저 관련 예외
    // 이메일 중복 처리
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<NoDataApiResponseDTO> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(HttpStatus.CONFLICT.value())  // 409 Conflict
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 사용자 존재하지 않음 예외 처리
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<NoDataApiResponseDTO> handleUserNotFound(UserNotFoundException ex) {
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(HttpStatus.NOT_FOUND.value())  // 404 Not Found
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // 조직 관련 예외
    // 중복된 조직 이름 예외 처리
    @ExceptionHandler(DuplicateOrganizationNameException.class)
    public ResponseEntity<NoDataApiResponseDTO> handleDuplicateOrganizationName(DuplicateOrganizationNameException ex) {
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(HttpStatus.CONFLICT.value())  // 409 Conflict
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 조직 생성시 필드 누락 예외
    @ExceptionHandler(InvalidOrganizationDataException.class)
    public ResponseEntity<NoDataApiResponseDTO> handleInvalidOrganizationData(InvalidOrganizationDataException ex) {
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value())  // 400 Bad Request
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 권한 부족 예외
    @ExceptionHandler(InsufficientPermissionException.class)
    public ResponseEntity<NoDataApiResponseDTO> handleInsufficientPermission(InsufficientPermissionException ex) {
        NoDataApiResponseDTO response = NoDataApiResponseDTO.builder()
                .code(HttpStatus.FORBIDDEN.value())  // 403 Forbidden
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
