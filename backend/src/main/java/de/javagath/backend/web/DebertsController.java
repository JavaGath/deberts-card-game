package de.javagath.backend.web;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.db.service.UserRegistrar;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class DebertsController {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  @Autowired UserRegistrar userRegistrar;

  @Autowired JwtUtil jwtUtil;

  @PostMapping(value = "/signup", consumes = "application/json")
  public ResponseEntity<String> signUp(@RequestBody SignUpDto singUpDto)
      throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException,
          UnrecoverableKeyException {
    LOG.info(singUpDto.toString());
    userRegistrar.registry(singUpDto);
    UserEntity user = new UserEntity();
    // ToDo: get user after Registration
    user.setEmail(singUpDto.getEmail());
    user.setName(singUpDto.getUsername());
    String token = jwtUtil.generateToken(user);
    return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(token);
  }

  @GetMapping(value = "/test")
  public String getTest()
      throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException,
          NoSuchAlgorithmException {

    LOG.info("Get Test");
    // ToDo: Tests for correct JWT and not
    String testToken =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJUZXN0IFN1YmplY3QiLCJlbWFpbCI6IlBsaXRvY2huaWswOEBnbWFpbC5jb20iLCJ1c2VybmFtZSI6IlBsaXRvY2huaWs6MC44IiwiaWF0IjoxNjUzNzMyODY2LCJleHAiOjE2NTM4MTkyNjZ9.NQHCQ-WU0rr22KGGUVdCmfS-cFcsYz8CqKegZgq1JEanL2uD-QH5UZsBIsN-V8xz3YObytfa_4V2oyi2Er-CoxM8v3vt0hw4INgR-pqP2-sHJHnooL2Lzxzs_is1xEpDSzijv0q5_Cmd-KaeBhP_fk8vXBJLg-o2krLK8OjmuHf79wX3HB0JTvxe1MyCBjg9kqW4sdYHirhNhV8aLGsg-SxFw8INxk5_90Ap-svKZ3_LCJb7F4d5432RJTrL-cf3gNKWaN0f4MFPmK7gb843nbtd-NBzouPuzMuk4iIkcuhOh354IbDLjgRmUPcjnd-OIWjICBy8cQ-9BUxzNIHEzA";
    jwtUtil.validateToken(testToken);

    return "Hello noname";
  }

  @PostMapping(value = "/test")
  public void postTest() {
    LOG.info("you did post");
  }
}
