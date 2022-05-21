package de.javagath.backend.web;

import de.javagath.backend.db.service.UserRegistrar;
import de.javagath.backend.web.model.SignUpDto;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping(value = "/signup", consumes = "application/json")
  public void signUp(@RequestBody SignUpDto singUpDto) {
    LOG.info(singUpDto.toString());
    userRegistrar.registry(singUpDto);
  }

  @GetMapping(value = "/test")
  public String getTest() {
    return "Hello noname";
  }

  @PostMapping(value = "/test")
  public void postTest() {
    LOG.info("you did post");
  }
}
