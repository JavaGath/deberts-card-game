package de.javagath.backend.web;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.db.service.UserService;
import de.javagath.backend.web.config.Constants;
import de.javagath.backend.web.model.LoginDto;
import de.javagath.backend.web.model.Response;
import de.javagath.backend.web.model.SignUpDto;
import de.javagath.backend.web.model.UserDto;
import de.javagath.backend.web.service.JwtService;
import java.lang.invoke.MethodHandles;
import javax.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController for authentication functionality like login and signup.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  /**
   * Constructor to autowire dependencies userService and jwtService.
   *
   * @param userService dependency of the bean UserService
   * @param jwtService dependency of the bean JwtService
   * @param authManager dependency of the bean AuthenticationManager
   */
  @Autowired
  public AuthenticationController(
      UserService userService, JwtService jwtService, AuthenticationManager authManager) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.authManager = authManager;
  }

  /**
   * Post method to registry new user in the application using email, username and password.
   *
   * @param singUpDto information for registration
   * @return new user
   */
  @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> signUp(@RequestBody SignUpDto singUpDto) {
    LOG.debug(singUpDto.toString());
    try {
      userService.registry(singUpDto);
      UserEntity userEntity = userService.selectUserByLogin(singUpDto.getEmail());
      ResponseEntity<Response> result = createSuccessfulResponse(userEntity);
      LOG.info("New player tries to sign up. Response status: " + result.getStatusCode());
      return result;
    } catch (ConstraintViolationException e) {
      LOG.info(
          "Username or Email of "
              + singUpDto.getUsername()
              + ", "
              + singUpDto.getEmail()
              + " is already registered. Please try to use unique email and username.");
      singUpDto.setErrorMsg(
          "Username or Email is already registered. Please try to use unique email and username.");
      return ResponseEntity.status(HttpStatus.CONFLICT).body(singUpDto);
    } catch (Exception e) {
      LOG.info("Registration of " + singUpDto.getUsername() + " was not successful.");
      singUpDto.setErrorMsg("Registration was not successful. Internal server error.");
      return ResponseEntity.internalServerError().body(singUpDto);
    }
  }

  /**
   * Post method to authenticate user in the application using login data and password.
   *
   * @param loginDto information for registration
   * @return authenticated user
   */
  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
    LOG.debug(loginDto.toString());
    try {
      UserEntity userEntity = userService.selectUserByLogin(loginDto.getLogin());
      UsernamePasswordAuthenticationToken authToken =
          userService.createUserPasswordAuthenticationToken(
              userEntity.getEmail(), loginDto.getPassword());
      authManager.authenticate(authToken);
      ResponseEntity<Response> result = createSuccessfulResponse(userEntity);
      LOG.info(
          "Player "
              + loginDto.getLogin()
              + " successfully logged in. Response status: "
              + result.getStatusCode());
      return result;
    } catch (BadCredentialsException | NoResultException e) {
      LOG.info("Login or password from player " + loginDto.getLogin() + " does not match.");
      loginDto.setErrorMsg("Login or password does not match. Please check your input data.");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginDto);
    } catch (Exception e) {
      LOG.info("Login of " + loginDto.getLogin() + " was not successful.");
      loginDto.setErrorMsg("Login was not successful. Internal server error.");
      return ResponseEntity.internalServerError().body(loginDto);
    }
  }

  private ResponseEntity<Response> createSuccessfulResponse(UserEntity userEntity) {
    String token = jwtService.generateToken(userEntity);
    UserDto user = new UserDto(userEntity, token);
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, Constants.BEARER + " " + token)
        .body(user);
  }
}
