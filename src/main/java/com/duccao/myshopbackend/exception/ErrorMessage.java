package com.duccao.myshopbackend.exception;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = PRIVATE)
public class ErrorMessage {
  UUID id = UUID.randomUUID();
  String message;

  public ErrorMessage(String message) {
    this.message = message;
  }
}
