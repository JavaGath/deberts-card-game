package de.javagath.backend.web.config;

import de.javagath.backend.db.model.UserEntity;
import de.javagath.backend.db.service.UserUtil;
import de.javagath.backend.web.service.JwtUtil;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
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

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  @Autowired JwtUtil jwtUtil;

  @Autowired UserUtil userUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    LOG.debug("Filter " + this.getClass().getName() + " is used");
    String authHeader = request.getHeader("Authorization");
    LOG.debug("AuthHeader: " + authHeader);
    if (authHeader != null && !authHeader.isBlank()) {
      String[] authContent = authHeader.split(" ");
      if (authContent.length != 2 || !authContent[0].equals("Bearer") || authContent[1].isBlank()) {
        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
      } else {
        String token = authContent[1];
        LOG.info(token);
        boolean isValid = false;
        try {
          isValid = jwtUtil.validateToken(token);
        } catch (Exception e) {
          response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
        if (isValid) {
          try {
            Map<String, String> claims = jwtUtil.getBody(token);
            LOG.info("My claims: " + claims);
            UserEntity user = userUtil.selectUserByEmail(claims.get("email"));
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), null);
            SecurityContextHolder.getContext().setAuthentication(authToken);
          } catch (Exception e) {
            LOG.info(e.toString());
          }
          LOG.info("I will implement you later");
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
