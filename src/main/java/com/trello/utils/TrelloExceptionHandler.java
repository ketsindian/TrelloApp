package com.trello.utils;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class TrelloExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<TrelloExceptionResponse> resourceNotFound(ResourceNotFoundException e, WebRequest request) {
        TrelloExceptionResponse errors = new TrelloExceptionResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(e.getMessage());
        errors.setErrorCode(HttpStatus.NOT_FOUND.value());
        errors.setDetails(request.getDescription(false));
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<TrelloExceptionResponse> serverError(Exception e, WebRequest request) {
        TrelloExceptionResponse errors = new TrelloExceptionResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(e.getMessage());
        errors.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.setDetails(request.getDescription(false));
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public final ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        TrelloExceptionResponse exceptionResponse = new TrelloExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setMessage("Validation Failed at : " + ex.getMessage());
        exceptionResponse.setDetails(request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity handleDataIntegrityViolationException(ConstraintViolationException ex, WebRequest request) {
        TrelloExceptionResponse exceptionResponse = new TrelloExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setMessage("DB constraint violated : " + ex.getMessage());
        exceptionResponse.setDetails(request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TrelloAuthenticationException.class)
    public final ResponseEntity<TrelloAuthenticationException> resourceNotFound(TrelloAuthenticationException e, WebRequest request) {
        TrelloExceptionResponse errors = new TrelloExceptionResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(e.getMessage());
        errors.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        errors.setDetails(request.getDescription(false));
        return new ResponseEntity(errors, HttpStatus.UNAUTHORIZED);
    }
}