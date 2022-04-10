package com.duccao.myshopbackend.config;

import static lombok.AccessLevel.PRIVATE;

import com.duccao.myshopbackend.services.UserDetailsService;
import com.duccao.myshopbackend.utils.TokenHelper;
import java.util.Arrays;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  TokenHelper tokenHelper;

  UserDetailsService userDetailsService;

  RestAuthenticationEntryPoint authenticationEntryPoint;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers(
            HttpMethod.GET,
            "/",
            "/webjars/**",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/assets/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/validate/**",
            "/v3/**",
            "/actuator/**",
            "/no-auth/**")
        .permitAll() // dont authenticate this particular request
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            new AuthorizationFilter(userDetailsService, tokenHelper),
            UsernamePasswordAuthenticationFilter
                .class); // Add a filter to validate the tokens with every request
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // TokenAuthenticationFilter will ignore the below paths
    web.ignoring()
        .antMatchers(
            HttpMethod.POST,
            "/users/register",
            "/swagger-ui",
            "/v3/**",
            "/swagger-ui.html",
            "/login",
            "/admin/login",
            "/no-auth/**");
    web.ignoring()
        .antMatchers(
            HttpMethod.GET,
            "/",
            "/users/inactive",
            "/users/is-deactivate/**",
            "/webjars/**",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/assets/**",
            "/actuator/**");
  }

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Collections.singletonList("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    config.setMaxAge(1800L);
    config.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
