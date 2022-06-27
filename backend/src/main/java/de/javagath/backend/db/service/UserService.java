package de.javagath.backend.db.service;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.web.config.Constants;
import de.javagath.backend.web.model.SignUpDto;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Utility class to handle operations with user and hist entity.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserService implements UserDetailsService {
  private static final String PEPPER = "G8pfjFp34fLBew1eg5k";
  private static final String BCRYPT_INFO = "$2a$10$";
  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z\\d._%+-]+@[A-Z\\d.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
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
    session.close();
    LOG.debug("User is saved");
  }

  /**
   * Selects user using his email address.
   *
   * @param email users email address
   * @return selected users entity
   */
  private UserEntity selectUserByEmail(String email) {
    Session session = hibernateFactory.openSession();
    Query query = session.createQuery("from UserEntity u where u.email = :email");
    query.setParameter(Constants.EMAIL_PARAMETER_KEY, email);
    UserEntity result = (UserEntity) query.getSingleResult();
    session.close();
    return result;
  }

  /**
   * Selects user using his username.
   *
   * @param username username
   * @return selected users entity
   */
  private UserEntity selectUserByUsername(String username) {
    Session session = hibernateFactory.openSession();
    Query query = session.createQuery("from UserEntity u where u.name = :username");
    query.setParameter(Constants.USERNAME_PARAMETER_KEY, username);
    UserEntity result = (UserEntity) query.getSingleResult();
    session.close();
    return result;
  }

  /**
   * Selects user using by login string.
   *
   * @param login login string
   * @return selected users entity
   */
  public UserEntity selectUserByLogin(String login) {
    return isEmail(login) ? selectUserByEmail(login) : selectUserByUsername(login);
  }

  private boolean isEmail(String emailStr) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    return matcher.find();
  }

  private String encodePasswordUsingSalt(String password, String salt) {
    LOG.debug("Plain Password: " + password);
    int passwordValue = password.hashCode() + PEPPER.hashCode();
    String passwordBcryptHashed = BCrypt.hashpw(Integer.toString(passwordValue), salt);
    LOG.debug("Full Password: " + passwordBcryptHashed);
    String passwordReady = passwordBcryptHashed.substring(salt.length());
    LOG.debug("Password to Store: " + passwordReady);
    return passwordReady;
  }

  private UserEntity createUserEntity(SignUpDto signUpDto) {
    UserEntity newUser = new UserEntity();
    newUser.setName(signUpDto.getUsername());
    newUser.setEmail(signUpDto.getEmail());
    String salt = BCrypt.gensalt();
    LOG.debug("My Salt Is: " + salt);
    newUser.setSalt(salt.substring(BCRYPT_INFO.length()));
    String passwordFinal = encodePasswordUsingSalt(signUpDto.getPassword(), salt);

    newUser.setPassword(passwordFinal);
    return newUser;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userRes = selectUserByLogin(username);
    if (userRes == null) throw new UsernameNotFoundException("Could not findUser");
    String dbBcryptPwd = UserService.BCRYPT_INFO + userRes.getSalt() + userRes.getPassword();
    return new User(
        userRes.getEmail(),
        dbBcryptPwd,
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
  }

  /**
   * Creates UserPasswordAuthenticationToken for user Authentication.
   *
   * @param login email or username
   * @param password password
   * @return UsernamePasswordAuthenticationToken for AuthenticationManagerBean
   */
  public UsernamePasswordAuthenticationToken createUserPasswordAuthenticationToken(
      String login, String password) {
    UserEntity userEntity = selectUserByLogin(login);
    String passwordValue = String.valueOf(password.hashCode() + PEPPER.hashCode());
    return new UsernamePasswordAuthenticationToken(
        userEntity.getEmail(),
        passwordValue,
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
  }
}
