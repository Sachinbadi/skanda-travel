package com.skanda.travels.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

  private String token;
  private Long userId;
  private String username;
  private Set<String> roles;

  public AuthResponse () {}

  public AuthResponse (String token, Long userId, String username, Set<String> roles) {
    this.token = token;
    this.userId = userId;
    this.username = username;
    this.roles = roles;
  }

}
