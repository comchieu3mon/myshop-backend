package com.duccao.myshopbackend.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {
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
}
