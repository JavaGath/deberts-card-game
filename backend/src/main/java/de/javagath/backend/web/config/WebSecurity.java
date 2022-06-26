package de.javagath.backend.web.config;

import de.javagath.backend.db.service.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
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
  private final UserService userService;

  /**
   * DI of new implementation of WebSecurityConfigurerAdapter to manage HttpSecurity of the
   * application.
   *
   * @param jwtTokenFilter DI to authenticate via JWT
   * @param historyModeFilter DI to support Vue HTML5 History Mode
   * @param userService DI to support Authentication
   */
  @Autowired
  public WebSecurity(
      JwtTokenFilter jwtTokenFilter, HistoryModeFilter historyModeFilter, UserService userService) {
    this.jwtTokenFilter = jwtTokenFilter;
    this.historyModeFilter = historyModeFilter;
    this.userService = userService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
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
        .antMatchers(
            "/img/**", "/css/**", "/js/**", "/api/auth/**", "/signup", "/login", "/actuator/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        .and()
        .addFilterAfter(historyModeFilter, FilterSecurityInterceptor.class)
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * Current encoder to work with passwords in Spring logic.
   *
   * @return password encoder for the app
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
