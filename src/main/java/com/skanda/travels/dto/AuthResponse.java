package com.skanda.travels.dto;

import java.util.Set;

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

  public String getToken () {
    return token;
  }

  public void setToken (String token) {
    this.token = token;
  }

  public Long getUserId () {
    return userId;
  }

  public void setUserId (Long userId) {
    this.userId = userId;
  }

  public String getUsername () {
    return username;
  }

  public void setUsername (String username) {
    this.username = username;
  }

  public Set<String> getRoles () {
    return roles;
  }

  public void setRoles (Set<String> roles) {
    this.roles = roles;
  }
}
