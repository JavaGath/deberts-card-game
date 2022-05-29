package de.javagath.backend.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private JwtTokenFilter jwtTokenFilter;

  @Autowired
  public WebSecurity(JwtTokenFilter jwtTokenFilter) {
    this.jwtTokenFilter = jwtTokenFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Enable CORS and disable CSRF. CORS will protect the app from CSRF attacks.
    http.cors()
        .and()
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        // Set stateless session-management
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // Set unauthorized requests exception handler
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(
            (request, response, authException) -> {
              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            })
        // Set permissions on endpoints
        .and()
        .authorizeRequests()
        // TODO: Change after JWT and Vue router fix
        .antMatchers("/user/signup/")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        // Add JwtTokenFilter
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
