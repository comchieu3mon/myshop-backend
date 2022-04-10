package com.duccao.myshopbackend.exception;

public class InvalidRequestException extends Exception {
  public InvalidRequestException() {
    super();
  }

  public InvalidRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidRequestException(final String message) {
    super(message);
  }

  public InvalidRequestException(final Throwable cause) {
    super(cause);
  }
}
