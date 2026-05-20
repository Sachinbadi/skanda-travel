package com.skanda.travels.error;

/** Entity not present or not visible */
public class NotFoundException extends RuntimeException {

  public NotFoundException (String message) {
    super(message);
  }
}
