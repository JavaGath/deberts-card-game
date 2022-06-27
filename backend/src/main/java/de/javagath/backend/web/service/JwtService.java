package de.javagath.backend.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.web.config.Constants;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Jwt util service to handle basic operations with JWT inside the app.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Component
public class JwtService implements InitializingBean {

  @Value("${keystore.path}")
  private String path;

  @Value("${keystore.password}")
  private String password;

  @Value("${key.alias}")
  private String alias;

  private Key key;

  /**
   * Generates Token using UserEntity. Each token has an expiration date for one day.
   *
   * @param user UserEntity from database
   * @return generated Token
   */
  public String generateToken(UserEntity user) {
    LocalDateTime currentTime = LocalDateTime.now();
    Date issuedAt = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
    Date expiration = Date.from(currentTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

    return Jwts.builder()
        .setHeaderParam(Constants.TOKEN_TYPE_KEY, Constants.TOKEN_TYPE)
        .setSubject(user.getId().toString())
        .claim(Constants.EMAIL_PARAMETER_KEY, user.getEmail())
        .claim(Constants.USERNAME_PARAMETER_KEY, user.getName())
        .setIssuedAt(issuedAt)
        .setExpiration(expiration)
        .signWith(key)
        .compact();
  }

  /**
   * Validates token using the private key. Returns true if the token is valid.
   *
   * @param token JWT-Token in String format
   * @return true if token is valid
   */
  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parse(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  /**
   * Returns body from the JWT-Token.
   *
   * @param token JWT-Token
   * @return body data
   */
  @SuppressWarnings("unchecked")
  public Map<String, String> getBody(String token) {
    ObjectMapper mapper = new ObjectMapper();
    Object json = Jwts.parserBuilder().setSigningKey(key).build().parse(token).getBody();

    return mapper.convertValue(json, Map.class);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    try (FileInputStream is = new FileInputStream(path)) {
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      keyStore.load(is, password.toCharArray());
      key = keyStore.getKey(alias, password.toCharArray());
    }
  }
}
