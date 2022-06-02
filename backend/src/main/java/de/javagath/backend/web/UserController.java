package de.javagath.backend.web;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.db.service.UserUtil;
import de.javagath.backend.web.model.SignUpDto;
import de.javagath.backend.web.service.JwtUtil;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  @Autowired UserUtil userUtil;

  @Autowired JwtUtil jwtUtil;

  @PostMapping(value = "/signup", consumes = "application/json")
  public ResponseEntity<String> signUp(@RequestBody SignUpDto singUpDto)
      throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException,
          UnrecoverableKeyException {
    LOG.info(singUpDto.toString());
    userUtil.registry(singUpDto);
    UserEntity user = new UserEntity();
    // ToDo: get user after Registration
    user.setEmail(singUpDto.getEmail());
    user.setName(singUpDto.getUsername());
    String token = jwtUtil.generateToken(user);
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(token);
  }
}
