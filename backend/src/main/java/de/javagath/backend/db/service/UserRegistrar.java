package de.javagath.backend.db.service;

import de.javagath.backend.db.model.UserDto;
import de.javagath.backend.web.model.SignUpDto;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrar {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private static final String PEPPER_VALUE = "G8pfjFp34fLBew1eg5k";

  void registry(SignUpDto signUpDto) {
    UserDto newUser = new UserDto();
    newUser.setName(signUpDto.getUsername());
    newUser.setEmail(signUpDto.getEmail());
    String salt = BCrypt.gensalt();
    LOG.info(salt);
  }
}
