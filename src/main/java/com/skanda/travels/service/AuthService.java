package com.skanda.travels.service;

import com.skanda.travels.dto.AuthResponse;
import com.skanda.travels.dto.LoginRequest;
import com.skanda.travels.dto.RegisterRequest;
import com.skanda.travels.entity.User;
import com.skanda.travels.enums.SecurityRole;
import com.skanda.travels.exception.BadRequestException;
import com.skanda.travels.repository.UserRepository;
import com.skanda.travels.security.AuthenticatedUserDetails;
import com.skanda.travels.security.JwtService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService (
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public AuthResponse register (RegisterRequest req) {
    String email = req.getEmail().trim();
    String username = req.getUsername().trim();

    if (userRepository.existsByEmailIgnoreCase(email)) {
      throw new BadRequestException("Email already registered");
    }
    if (userRepository.existsByUsernameIgnoreCase(username)) {
      throw new BadRequestException("Username already taken");
    }

    User user = new User();
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(req.getPassword()));
    user.setPhone(req.getPhone() == null ? null : req.getPhone().trim());
    user.getRoles().add(SecurityRole.ROLE_USER);
    user = userRepository.save(user);

    String token = jwtService.generateToken(
        user.getUsername(),
        user.getRoles().stream().map(SecurityRole::name).collect(Collectors.toSet())
    );
    return new AuthResponse(
        token,
        user.getId(),
        user.getUsername(),
        user.getRoles().stream().map(SecurityRole::name).collect(Collectors.toSet())
    );
  }

  public AuthResponse login (LoginRequest req) {
    String key = req.getUsernameOrEmail().trim();
    User user = userRepository.findByUsernameIgnoreCase(key)
        .or(() -> userRepository.findByEmailIgnoreCase(key))
        .orElseThrow(() -> new BadCredentialsException("invalid"));

    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), req.getPassword())
    );

    String token = jwtService.generateToken(auth);
    AuthenticatedUserDetails principal = (AuthenticatedUserDetails) auth.getPrincipal();
    User persisted = principal.getUser();
    return new AuthResponse(
        token,
        persisted.getId(),
        persisted.getUsername(),
        persisted.getRoles().stream().map(SecurityRole::name).collect(Collectors.toSet())
    );
  }
}
