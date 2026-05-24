package com.skanda.travels.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "skanda.jwt")
@Getter @Setter
public class SkandaJwtProperties {

  private String secret;
  /** Access token TTL in milliseconds */
  private long expirationMs = 86_400_000L;

}
