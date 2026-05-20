package com.skanda.travels;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String baseUrl () {
    return "http://localhost:" + port;
  }

  @Test
  void happyPath_registerLoginSearchAndBookTrip () {
    String unique = UUID.randomUUID().toString().substring(0, 8);
    String email = "user_" + unique + "@example.com";
    String username = "user_" + unique;

    Map<String, Object> registerBody = Map.of(
        "email", email,
        "username", username,
        "password", "Password123",
        "phone", "9000000001"
    );

    ResponseEntity<Map> registerResp = restTemplate.postForEntity(
        baseUrl() + "/api/auth/register",
        registerBody,
        Map.class
    );

    assertThat(registerResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(registerResp.getBody()).isNotNull();
    String token = (String) registerResp.getBody().get("token");
    assertThat(token).isNotBlank();

    LocalDate tomorrow = LocalDate.now().plusDays(1);
    String searchUrl = baseUrl()
        + "/api/trips/search?from=Jindal&to=Mangalore&date="
        + tomorrow
        + "&passengers=1";

    ResponseEntity<List> searchResp = restTemplate.getForEntity(searchUrl, List.class);
    assertThat(searchResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(searchResp.getBody()).isNotNull();
    assertThat(searchResp.getBody()).isNotEmpty();

    Map<?, ?> firstTrip = (Map<?, ?>) searchResp.getBody().get(0);
    Number tripId = (Number) firstTrip.get("id");
    assertThat(tripId).isNotNull();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);

    Map<String, Object> bookingBody = Map.of(
        "tripId", tripId.longValue(),
        "couponCode", "SKANDA10",
        "passengers", List.of(
            Map.of("fullName", "John Doe", "seatNumber", 1)
        )
    );

    ResponseEntity<Map> bookingResp = restTemplate.exchange(
        baseUrl() + "/api/bookings",
        HttpMethod.POST,
        new HttpEntity<>(bookingBody, headers),
        Map.class
    );

    assertThat(bookingResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(bookingResp.getBody()).isNotNull();
    String bookingRef = (String) bookingResp.getBody().get("bookingReference");
    assertThat(bookingRef).isNotBlank();

    ResponseEntity<Map> bookingByRef = restTemplate.exchange(
        baseUrl() + "/api/bookings/reference/" + bookingRef,
        HttpMethod.GET,
        new HttpEntity<>(null, headers),
        Map.class
    );

    assertThat(bookingByRef.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(bookingByRef.getBody()).isNotNull();
    assertThat(bookingByRef.getBody().get("bookingReference")).isEqualTo(bookingRef);

    ResponseEntity<String> myBookings = restTemplate.exchange(
        baseUrl() + "/api/bookings",
        HttpMethod.GET,
        new HttpEntity<>(null, headers),
        String.class
    );
    assertThat(myBookings.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(myBookings.getBody()).isNotBlank();
    assertThat(myBookings.getBody().trim()).startsWith("[");
  }

  @Test
  void edgeCases_authAndBooking () {
    Map<String, Object> registerBody = Map.of(
        "email", "demo@skandatravels.test",
        "username", "demo",
        "password", "Password123",
        "phone", "9000000000"
    );

    ResponseEntity<Map> conflictResp = restTemplate.postForEntity(
        baseUrl() + "/api/auth/register",
        registerBody,
        Map.class
    );

    assertThat(conflictResp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    ResponseEntity<Map> unauthorizedBookings = restTemplate.getForEntity(
        baseUrl() + "/api/bookings",
        Map.class
    );
    assertThat(unauthorizedBookings.getStatusCode().is4xxClientError()).isTrue();

    LocalDate tomorrow = LocalDate.now().plusDays(1);
    String searchUrl = baseUrl()
        + "/api/trips/search?from=Jindal&to=Mangalore&date="
        + tomorrow
        + "&passengers=1";

    ResponseEntity<List> searchResp = restTemplate.getForEntity(searchUrl, List.class);
    assertThat(searchResp.getStatusCode()).isEqualTo(HttpStatus.OK);

    if (searchResp.getBody() == null || searchResp.getBody().isEmpty()) {
      return;
    }

    Map<?, ?> firstTrip = (Map<?, ?>) searchResp.getBody().get(0);
    Number tripId = (Number) firstTrip.get("id");

    Map<String, Object> loginBody = Map.of(
        "usernameOrEmail", "demo",
        "password", "Travel123"
    );

    ResponseEntity<Map> loginResp = restTemplate.postForEntity(
        baseUrl() + "/api/auth/login",
        loginBody,
        Map.class
    );
    assertThat(loginResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(loginResp.getBody()).isNotNull();
    String token = (String) loginResp.getBody().get("token");
    assertThat(token).isNotBlank();

    Map<String, Object> bookingBody = Map.of(
        "tripId", tripId.longValue(),
        "couponCode", "INVALID_COUPON",
        "passengers", List.of(
            Map.of("fullName", "Edge Case User", "seatNumber", 2)
        )
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);

    ResponseEntity<Map> invalidCouponResp = restTemplate.exchange(
        baseUrl() + "/api/bookings",
        HttpMethod.POST,
        new HttpEntity<>(bookingBody, headers),
        Map.class
    );

    assertThat(invalidCouponResp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }
}

