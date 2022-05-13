package de.javagath.backend.web;

import de.javagath.backend.web.model.SignUpDto;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/user")
public class DebertsController {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  @PostMapping(value = "/signup", consumes = "application/json")
  public void signUp(@RequestBody SignUpDto singUpDto) {
    LOG.info(singUpDto.toString());
  }
}
