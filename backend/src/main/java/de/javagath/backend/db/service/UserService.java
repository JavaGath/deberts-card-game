package de.javagath.backend.db.service;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.web.config.Constants;
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

/**
 * Utility class to handle operations with user and hist entity.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserService {

  private static final String BCRYPT_INFO = "$2a$10$";
  private static final String PEPPER = "G8pfjFp34fLBew1eg5k";
  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final SessionFactory hibernateFactory;

  /**
   * Constructor for Service entity. EntityManagerFactory is needed to allow some Hibernate
   * operations with UserEntity.
   *
   * @param factory Hibernate Entity Manager Factory
   */
  @Autowired
  public UserService(EntityManagerFactory factory) {
    if (factory.unwrap(SessionFactory.class) == null) {
      throw new NullPointerException("factory is not a hibernate factory");
    }
    this.hibernateFactory = factory.unwrap(SessionFactory.class);
  }

  /**
   * Registries new user in the database from sign-up data.
   *
   * @param signUpDto sign-up data
   */
  public void registry(SignUpDto signUpDto) {
    UserEntity newUser = createUserEntity(signUpDto);
    Session session = hibernateFactory.openSession();
    LOG.debug("Try to save user");
    session.save(newUser);
    LOG.debug("User is saved");
  }

  /**
   * Selects user using his email address.
   *
   * @param email users email address.
   * @return selected users entity
   */
  public UserEntity selectUserByEmail(String email) {
    Session session = hibernateFactory.openSession();
    Query query = session.createQuery("from UserEntity u where u.email = :email");
    query.setParameter(Constants.EMAIL_PARAMETER_KEY, email);
    return (UserEntity) query.getSingleResult();
  }

  private String encodePassword(String password, String salt) {
    LOG.debug("Plain Password: " + password);
    int passwordValue = password.hashCode() + PEPPER.hashCode();
    String passwordBcrypt = BCrypt.hashpw(Integer.toString(passwordValue), salt);
    String passwordEncoded = passwordEncoder.encode(passwordBcrypt);
    String passwordReady = passwordEncoded.substring(BCRYPT_INFO.length());
    LOG.debug("Password to Store: " + passwordReady);
    return passwordReady;
  }

  private UserEntity createUserEntity(SignUpDto signUpDto) {
    UserEntity newUser = new UserEntity();
    newUser.setName(signUpDto.getUsername());
    newUser.setEmail(signUpDto.getEmail());
    String salt = BCrypt.gensalt();
    newUser.setSalt(salt.substring(BCRYPT_INFO.length()));
    String password = encodePassword(signUpDto.getPassword(), salt);
    newUser.setPassword(password);
    return newUser;
  }
}
