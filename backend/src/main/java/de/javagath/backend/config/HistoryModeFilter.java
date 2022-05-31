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

/**
 * Enables HTML5 history mode for the embedded tomcat server. Single page applications use history
 * mode to connect to other addresses. Servers like Node.js supports as default history mode for
 * JS-Sources but tomcat does not. This filter implements this functionality for tomcat.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Component
public class HistoryModeFilter extends OncePerRequestFilter {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private final Pattern pattern = Pattern.compile("^/([^.])*");

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    LOG.debug(request.getRequestURI());
    if (pattern.matcher(request.getRequestURI()).matches()) {
      LOG.debug("URI is matched");
      String endpoint = "/";
      RequestDispatcher rd = request.getRequestDispatcher(endpoint);
      rd.forward(request, response);
    } else {
      LOG.debug("URI is not matched");
      filterChain.doFilter(request, response);
    }
  }
}
