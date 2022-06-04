package de.javagath.backend.web.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration Bean for Web Security. It enables CORS to act with Vue-Frontend and supports
 * JWT-authentication in the application. Additionally, it uses the HistoryModeFilter to support Vue
 * HTML5 History Mode.
 *
 * @author Ievgenii Izrailtenko
 * @version 1.0
 * @since 1.0
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final JwtTokenFilter jwtTokenFilter;
  private final HistoryModeFilter historyModeFilter;

  /**
   * DI of new implementation of WebSecurityConfigurerAdapter to manage HttpSecurity of the
   * application.
   *
   * @param jwtTokenFilter DI to authenticate via JWT
   * @param historyModeFilter DI to support Vue HTML5 History Mode
   */
  @Autowired
  public WebSecurity(JwtTokenFilter jwtTokenFilter, HistoryModeFilter historyModeFilter) {
    this.jwtTokenFilter = jwtTokenFilter;
    this.historyModeFilter = historyModeFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(
            (request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()))
        .and()
        .authorizeRequests()
        .antMatchers("/img/**", "/css/**", "/js/**", "/api/auth/**", "/sign-up")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterAfter(historyModeFilter, FilterSecurityInterceptor.class)
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
