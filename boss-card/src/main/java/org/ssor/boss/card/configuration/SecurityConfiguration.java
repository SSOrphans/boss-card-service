package org.ssor.boss.card.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.ssor.boss.core.filter.AuthenticationFilter;
import org.ssor.boss.core.filter.VerificationFilter;
import org.ssor.boss.core.service.UserService;
import java.util.List;
import static org.ssor.boss.core.entity.UserType.USER_ADMIN;
import static org.ssor.boss.core.entity.UserType.USER_HOLDER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider()
  {
    final var authProvider = new DaoAuthenticationProvider();
    authProvider.setPasswordEncoder(passwordEncoder);
    authProvider.setUserDetailsService(userService);
    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    // H2 only!
    http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
    http.headers().frameOptions().disable();
    // H2 only!

    final var holderAuth = USER_HOLDER.toString();
    final var adminAuth = USER_ADMIN.toString();
    http.cors()
        .configurationSource(corsConfigurationSource())
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        .requestMatchers(CorsUtils::isCorsRequest).permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .antMatchers(HttpMethod.GET, "/api/v*/cards/{\\d+}").hasAuthority(adminAuth)
        .antMatchers(HttpMethod.PUT, "/api/v*/cards/{\\d+}").hasAuthority(adminAuth)
        .antMatchers(HttpMethod.DELETE, "/api/v*/cards/{\\d+}").hasAuthority(adminAuth)
        .antMatchers(HttpMethod.GET, "/api/v*/cards").hasAuthority(adminAuth)
        .antMatchers(HttpMethod.POST, "/api/v*/cards").hasAnyAuthority(holderAuth, adminAuth)
        // Add paths here.
        .anyRequest().authenticated()
        .and()
        .addFilter(new AuthenticationFilter(authenticationManager()))
        .addFilter(new VerificationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  private CorsConfigurationSource corsConfigurationSource()
  {
    final var configuration = new CorsConfiguration();
    configuration.setAllowedMethods(List.of(
        HttpMethod.GET.name(),
        HttpMethod.PUT.name(),
        HttpMethod.POST.name(),
        HttpMethod.DELETE.name()
    ));

    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
    return source;
  }
}
