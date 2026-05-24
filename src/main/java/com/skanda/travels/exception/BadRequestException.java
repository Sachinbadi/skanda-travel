package com.skanda.travels.exception;

/** Client sent invalid payload or inconsistent booking request */
public class BadRequestException extends RuntimeException {

  public BadRequestException (String message) {
    super(message);
  }
}
