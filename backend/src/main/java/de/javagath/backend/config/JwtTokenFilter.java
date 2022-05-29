package de.javagath.backend.config;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    LOG.info("Filter is used");
    String authHeader = request.getHeader("Authorization");
    LOG.info("AuthHeader: " + authHeader);
    // If I have no Authorization header, then skip this filter
    if (authHeader != null && !authHeader.isBlank()) {
      String jwt = authHeader.substring(7);
      LOG.info(jwt);
      if (jwt == null || jwt.isBlank()) {
        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
      } else {

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                "ievgenii.izrailtenko@gmail.com", "123456", null);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println("I will implement you later");
      }
    }
    filterChain.doFilter(request, response);
  }
}
