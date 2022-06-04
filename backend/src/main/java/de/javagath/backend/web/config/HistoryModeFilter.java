package de.javagath.backend.web.config;

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
 * <p>javaApiPattern contains Java-Controllers for backend calls. These calls should not be
 * forwarded
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@Component
public class HistoryModeFilter extends OncePerRequestFilter {

  private static final Logger LOG =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
  private static final String ENDPOINT = "/";
  private final Pattern pattern = Pattern.compile("^/([^.])*");
  private final Pattern javaApiPattern = Pattern.compile("^.*/api/.*$");

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    LOG.debug(request.getRequestURI());
    if (pattern.matcher(request.getRequestURI()).matches()
        && !javaApiPattern.matcher(request.getRequestURI()).matches()) {
      LOG.debug("Pattern is matched and javaApiPattern is not matched");
      RequestDispatcher rd = request.getRequestDispatcher(ENDPOINT);
      rd.forward(request, response);
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
