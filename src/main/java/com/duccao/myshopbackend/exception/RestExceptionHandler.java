package com.duccao.myshopbackend.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = UserAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public ErrorMessage resourceNotFoundException(
      UserAlreadyExistException exception, WebRequest webRequest) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = InvalidRequestException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage badRequestException(Exception exception, WebRequest webRequest) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage illegalArgumentException(Exception exception, WebRequest webRequest) {
    return new ErrorMessage(exception.getMessage());
  }

  @ExceptionHandler(value = EntityNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage entityNotFoundException(Exception exception, WebRequest webRequest) {
    return new ErrorMessage(exception.getMessage());
  }

  @Override
  public ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorMessage("Request Endpoint not found"));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .map(StringUtils::capitalize)
            .sorted()
            .collect(Collectors.toList());
    ErrorMessage errorMessage = new ErrorMessage(String.join(". ", errors));
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
