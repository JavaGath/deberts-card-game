package de.javagath.backend.web.config;

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
    LOG.debug("Filter " + this.getClass().getName() + " is used");
    String authHeader = request.getHeader("Authorization");
    LOG.debug("AuthHeader: " + authHeader);
    if (authHeader != null && !authHeader.isBlank()) {
      String[] authContent = authHeader.split(" ");
      if (authContent.length != 2 || !authContent[0].equals("Bearer") || authContent[1].isBlank()) {
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
