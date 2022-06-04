package de.javagath.backend.web.config;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.db.service.UserService;
import de.javagath.backend.web.service.JwtService;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Enables authorization via JWT-Token. This class validates JWT, selects the user from the database
 * and authenticates him in spring security.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  private final JwtService jwtService;

  private final UserService userService;

  @Autowired
  JwtTokenFilter(JwtService jwtService, UserService userService) {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    LOG.debug("Filter " + this.getClass().getName() + " is used");
    String authHeader = request.getHeader(Constants.AUTH_HEADER);
    if (isAuthHeaderReady(authHeader)) {
      String token = getToken(authHeader);
      LOG.debug(token);
      if (jwtService.isTokenValid(token)) {
        handleAuthentication(token);
      } else {
        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
      }
    }
    filterChain.doFilter(request, response);
  }

  private void handleAuthentication(String token) {
    Map<String, String> claims = jwtService.getBody(token);
    LOG.debug("My claims: " + claims);
    UserEntity user = userService.selectUserByEmail(claims.get(Constants.EMAIL_PARAMETER_KEY));
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), null);
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  private boolean isAuthHeaderReady(String authHeader) {
    if (Objects.isNull(authHeader) || authHeader.isBlank()) {
      return false;
    }

    String[] authContent = authHeader.split(" ");
    if (authContent.length != 2
        || !authContent[0].equals(Constants.BEARER)
        || authContent[1].isBlank()) {
      return false;
    }

    return SecurityContextHolder.getContext().getAuthentication() == null;
  }

  private String getToken(String authHeader) {
    String[] authContent = authHeader.split(" ");
    return authContent[1];
  }
}
