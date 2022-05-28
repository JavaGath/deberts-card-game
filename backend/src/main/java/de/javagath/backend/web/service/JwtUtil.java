package de.javagath.backend.web.service;

import de.javagath.backend.db.model.UserEntity;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  @Value("${keystore.path}")
  String path;

  @Value("${cert.path}")
  String certPath;

  @Value("${keystore.password}")
  String password;

  @Value("${key.alias}")
  String alias;

  public String generateToken(UserEntity user)
      throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException,
          UnrecoverableKeyException {
    // ToDo: Create own service
    FileInputStream is = new FileInputStream(path);
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(is, password.toCharArray());
    Key key = keyStore.getKey(alias, password.toCharArray());

    LocalDateTime currentTime = LocalDateTime.now();
    Date issuedAt = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
    Date expiration = Date.from(currentTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject("Test Subject")
        .claim("email", user.getEmail())
        .claim("username", user.getName())
        .setIssuedAt(issuedAt)
        .setExpiration(expiration)
        .signWith(key)
        .compact();
  }

  public String validateToken(String token)
      throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException,
          UnrecoverableKeyException {
    // ToDo: Create own service
    FileInputStream is = new FileInputStream(path);
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    keyStore.load(is, password.toCharArray());
    Key key = keyStore.getKey(alias, password.toCharArray());

    try {

      Jwts.parserBuilder().setSigningKey(key).build().parse(token);
      System.out.println("WE CAN TRUST!!!!!!!");

    } catch (JwtException e) {
      System.out.println("NOT OK!!!!!!!" + e.toString());
    }

    return "test";
  }
}
