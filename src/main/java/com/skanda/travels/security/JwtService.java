package com.skanda.travels.security;

import com.skanda.travels.config.SkandaJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SkandaJwtProperties jwtProperties;

  public JwtService (SkandaJwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  public String extractUsername (String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean isTokenValid (String token, String expectedUsername) {
    try {
      String username = extractUsername(token);
      return username != null
          && username.equalsIgnoreCase(expectedUsername)
          && !isExpired(token);
    } catch (JwtException ex) {
      return false;
    }
  }

  public String generateToken (Authentication authentication) {
    java.util.Collection<String> roles = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
    return generateToken(authentication.getName(), roles);
  }

  public String generateToken (String username, java.util.Collection<String> roles) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + jwtProperties.getExpirationMs());

    return Jwts.builder()
        .subject(username)
        .issuedAt(now)
        .expiration(exp)
        .claim("roles", roles)
        .signWith(signingKey(), Jwts.SIG.HS256)
        .compact();
  }

  private boolean isExpired (String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  private Claims extractAllClaims (String token) {
    return Jwts.parser()
        .verifyWith(signingKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey signingKey () {
    byte[] secretBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(secretBytes);
  }
}
