package de.javagath.backend.web;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.db.service.UserService;
import de.javagath.backend.web.config.Constants;
import de.javagath.backend.web.model.SignUpDto;
import de.javagath.backend.web.service.JwtService;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  private final UserService userService;

  private final JwtService jwtService;

  /**
   * Constructor to autowire dependencies userService and jwtService.
   *
   * @param userService dependency of the bean UserService
   * @param jwtService dependency of the bean JwtService
   */
  @Autowired
  public AuthenticationController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  /**
   * Post method to registry new user in the application using email, username and password.
   *
   * @param singUpDto information for registration
   * @return new user
   */
  @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserEntity> signUp(@RequestBody SignUpDto singUpDto) {
    LOG.debug(singUpDto.toString());
    userService.registry(singUpDto);
    UserEntity user = userService.selectUserByEmail(singUpDto.getEmail());
    String token = Constants.BEARER + " " + jwtService.generateToken(user);
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(user);
  }
}
