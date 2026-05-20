package com.skanda.travels.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "skanda.jwt")
public class SkandaJwtProperties {

  private String secret;

  /** Access token TTL in milliseconds */
  private long expirationMs = 86_400_000L;

  public String getSecret () {
    return secret;
  }

  public void setSecret (String secret) {
    this.secret = secret;
  }

  public long getExpirationMs () {
    return expirationMs;
  }

  public void setExpirationMs (long expirationMs) {
    this.expirationMs = expirationMs;
  }
}
