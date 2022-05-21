package de.javagath.backend.db.service;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.web.model.SignUpDto;
import java.lang.invoke.MethodHandles;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrar {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private static final String PEPPER = "G8pfjFp34fLBew1eg5k";
  private static final String bcryptInfo = "$2a$10$";
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  private SessionFactory hibernateFactory;

  @Autowired
  public UserRegistrar(EntityManagerFactory factory) {
    if (factory.unwrap(SessionFactory.class) == null) {
      throw new NullPointerException("factory is not a hibernate factory");
    }
    this.hibernateFactory = factory.unwrap(SessionFactory.class);
  }

  public void registry(SignUpDto signUpDto) {
    LOG.info("Plain Password: " + signUpDto.getPassword());
    UserEntity newUser = new UserEntity();
    newUser.setName(signUpDto.getUsername());
    newUser.setEmail(signUpDto.getEmail());
    String salt = BCrypt.gensalt();
    LOG.info("Generated Salt: " + salt);
    newUser.setSalt(salt.substring(bcryptInfo.length()));
    // ToDo: Create own method
    Integer passwordValue = signUpDto.getPassword().hashCode() + PEPPER.hashCode();
    LOG.info("Password with Pepper in hash: " + passwordValue);
    String password = BCrypt.hashpw(passwordValue.toString(), salt);
    LOG.info("BcryptPassword: " + password);
    String passwordEncoded = passwordEncoder.encode(password);
    LOG.info("Password encoded: " + passwordEncoded);
    String passwordDb = passwordEncoded.substring(bcryptInfo.length());
    LOG.info("Password to Store: " + passwordDb);
    // End of the method
    newUser.setPassword(passwordDb);
    // ToDo: Create Unittest
    boolean test =
        passwordEncoder.matches(
            BCrypt.hashpw(
                passwordValue.toString(), bcryptInfo + salt.substring(bcryptInfo.length())),
            bcryptInfo + passwordDb);
    LOG.info("PasswordTest:" + test);

    Session session = hibernateFactory.openSession();
    LOG.info("Test Save Begin!");
    session.save(newUser);
    LOG.info("Test Save End!");
  }
}
