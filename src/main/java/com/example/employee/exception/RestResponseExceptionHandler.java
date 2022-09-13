package com.example.employee.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
        ErrorData error = new ErrorData(HttpStatus.BAD_REQUEST);
        error.setMessage("email id already exists. please select a unique address.");
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ErrorData error = new ErrorData(HttpStatus.NOT_FOUND);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity handleDataExistException(DataAlreadyExistsException ex) {
        ErrorData error = new ErrorData(HttpStatus.NOT_FOUND);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(DbNotUpdatedException.class)
    public ResponseEntity handleDbNotUpdatedException(DbNotUpdatedException ex) {
        ErrorData error = new ErrorData(HttpStatus.OK);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, ValidationException.class})
    public ResponseEntity handleConflict(ValidationException ex, WebRequest request) {
        ErrorData error = new ErrorData(HttpStatus.BAD_REQUEST);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    //handles exception from @RequestBody
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorData error = new ErrorData(HttpStatus.BAD_REQUEST);
        error.setMessage(Map.of("errors", ex.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.toList())).toString());
        return new ResponseEntity<>(error, error.getStatus());
    }
}
