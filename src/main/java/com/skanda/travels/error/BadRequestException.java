package com.skanda.travels.error;

/** Client sent invalid payload or inconsistent booking request */
public class BadRequestException extends RuntimeException {

  public BadRequestException (String message) {
    super(message);
  }
}
