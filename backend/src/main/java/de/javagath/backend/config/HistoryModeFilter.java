package de.javagath.backend.config;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class HistoryModeFilter extends OncePerRequestFilter {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  // Update the regex as you needed
  private Pattern patt = Pattern.compile("^/([^\\.])*");
  private String endpoint = "/";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    LOG.info(request.getRequestURI());
    if (this.patt.matcher(request.getRequestURI()).matches()) {
      LOG.info("I am in IF");
      RequestDispatcher rd = request.getRequestDispatcher(endpoint);
      rd.forward(request, response);
    } else {
      LOG.info("I am in ELSE");
      filterChain.doFilter(request, response);
    }
  }
}
