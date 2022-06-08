package de.javagath.backend.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.db.service.UserService;
import de.javagath.backend.web.config.Constants;
import de.javagath.backend.web.service.JwtService;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-unittest.properties")
@Sql(scripts = "/create-test-data.sql")
@Sql(scripts = "/cleanup-test-data.sql", executionPhase = AFTER_TEST_METHOD)
public class DefaultControllerTest {
  @LocalServerPort int port;
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private UserService userService;

  @Autowired private JwtService jwtService;

  @Test
  void index_callOfIndexPageWithoutJWT_http401() throws URISyntaxException {
    String baseUrl = "http://localhost:" + port + "/";

    URI uri = new URI(baseUrl);
    ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

    assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(response.getStatusCode());
  }

  @Test
  void index_callOfIndexPageWithJWT_http200() throws URISyntaxException {
    String baseUrl = "http://localhost:" + port + "/";
    String email = "Plitochnik@gmail.com";

    UserEntity selectedEntity = userService.selectUserByEmail(email);
    String tokenInHeader = Constants.BEARER + " " + jwtService.generateToken(selectedEntity);
    HttpHeaders headers = new HttpHeaders();
    headers.set(Constants.AUTH_HEADER, tokenInHeader);
    HttpEntity<Object> entity = new HttpEntity<>(headers);
    URI uri = new URI(baseUrl);
    ResponseEntity<String> response =
        restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

    assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
  }

  @Test
  void index_callOfIndexPageWithInvalidJWT_http500() throws URISyntaxException {
    String baseUrl = "http://localhost:" + port + "/";
    String email = "Plitochnik@gmail.com";

    UserEntity selectedEntity = userService.selectUserByEmail(email);
    String tokenInHeader =
        Constants.BEARER + " " + jwtService.generateToken(selectedEntity).substring(5);
    HttpHeaders headers = new HttpHeaders();
    headers.set(Constants.AUTH_HEADER, tokenInHeader);
    HttpEntity<Object> entity = new HttpEntity<>(headers);
    URI uri = new URI(baseUrl);
    ResponseEntity<String> response =
        restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

    assertThat(HttpStatus.INTERNAL_SERVER_ERROR).isEqualTo(response.getStatusCode());
  }

  @Test
  void index_callOfIndexPageWithNoTypeToken_http401() throws URISyntaxException {
    String baseUrl = "http://localhost:" + port + "/";
    String email = "Plitochnik@gmail.com";

    UserEntity selectedEntity = userService.selectUserByEmail(email);
    String tokenInHeader = jwtService.generateToken(selectedEntity);
    HttpHeaders headers = new HttpHeaders();
    headers.set(Constants.AUTH_HEADER, tokenInHeader);
    HttpEntity<Object> entity = new HttpEntity<>(headers);
    URI uri = new URI(baseUrl);
    ResponseEntity<String> response =
        restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

    assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(response.getStatusCode());
  }
}
