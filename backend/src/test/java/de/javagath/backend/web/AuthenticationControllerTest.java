package de.javagath.backend.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import de.javagath.backend.web.config.Constants;
import java.net.URI;
import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-unittest.properties")
@Sql(scripts = "/create-test-data.sql")
@Sql(scripts = "/cleanup-test-data.sql", executionPhase = AFTER_TEST_METHOD)
public class AuthenticationControllerTest {
  @LocalServerPort int port;
  private String baseUrl;
  private JSONObject json;
  private HttpHeaders headers;
  @Autowired private TestRestTemplate restTemplate;

  @BeforeEach
  public void init() {
    baseUrl = "http://localhost:" + port + "/api/auth";
    json = new JSONObject();
    headers = new HttpHeaders();
    headers.set(Constants.CONTENT_TYPE_HEADER, MediaType.APPLICATION_JSON_VALUE);
    headers.set(Constants.ACCEPT_HEADER, MediaType.APPLICATION_JSON_VALUE);
  }

  @Test
  void signUp_newUniqueUser_http200() throws JSONException, URISyntaxException {
    json.put("username", "JavaGath123");
    json.put("email", "javagath@test.com");
    json.put("password", "crackMe");

    HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
    URI uri = new URI(baseUrl + "/signup");
    ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void login_existedUserRightPassword_http200() throws JSONException, URISyntaxException {
    json.put("login", "Plitochnik");
    json.put("password", "123456");

    HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
    URI uri = new URI(baseUrl + "/login");
    ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
