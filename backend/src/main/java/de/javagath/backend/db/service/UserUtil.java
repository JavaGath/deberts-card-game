package de.javagath.backend.db.service;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.web.model.SignUpDto;
import java.lang.invoke.MethodHandles;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserUtil {

  static final String bcryptInfo = "$2a$10$";
  private static final String PEPPER = "G8pfjFp34fLBew1eg5k";
  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private SessionFactory hibernateFactory;

  @Autowired
  public UserUtil(EntityManagerFactory factory) {
    if (factory.unwrap(SessionFactory.class) == null) {
      throw new NullPointerException("factory is not a hibernate factory");
    }
    this.hibernateFactory = factory.unwrap(SessionFactory.class);
  }

  public void registry(SignUpDto signUpDto) {

    UserEntity newUser = createUserEntity(signUpDto);

    Session session = hibernateFactory.openSession();
    LOG.info("Test Save Begin!");
    session.save(newUser);
    LOG.info("Test Save End!");
  }

  public UserEntity selectUserByEmail(String email) {
    Session session = hibernateFactory.openSession();
    Query query = session.createQuery("from UserEntity u where u.email = :email");
    query.setParameter("email", email);
    return (UserEntity) query.getSingleResult();
  }

  private String encodePassword(String password, String salt) {
    LOG.debug("Plain Password: " + password);
    Integer passwordValue = password.hashCode() + PEPPER.hashCode();
    String passwordBcrypt = BCrypt.hashpw(passwordValue.toString(), salt);
    String passwordEncoded = passwordEncoder.encode(passwordBcrypt);
    String passwordReady = passwordEncoded.substring(bcryptInfo.length());
    LOG.debug("Password to Store: " + passwordReady);
    return passwordReady;
  }

  private UserEntity createUserEntity(SignUpDto signUpDto) {
    UserEntity newUser = new UserEntity();
    newUser.setName(signUpDto.getUsername());
    newUser.setEmail(signUpDto.getEmail());
    String salt = BCrypt.gensalt();
    newUser.setSalt(salt.substring(bcryptInfo.length()));
    String password = encodePassword(signUpDto.getPassword(), salt);
    newUser.setPassword(password);
    return newUser;
  }
}
