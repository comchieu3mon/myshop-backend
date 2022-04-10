package com.duccao.myshopbackend.config;

import static lombok.AccessLevel.PRIVATE;

import com.duccao.myshopbackend.services.UserDetailsService;
import com.duccao.myshopbackend.utils.TokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
  @NonNull UserDetailsService userDetailsService;
  @NonNull TokenHelper tokenHelper;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader("Authorization");
    String username = null;
    String token = null;
    log.info("Checking JWT Token for request url={}", request.getRequestURI());
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      token = requestTokenHeader.substring(7);

      // TODO: this flow need to be reviewed. Why only log, but not throw error?
      try {
        username = tokenHelper.getUsernameFromToken(token);
      } catch (IllegalArgumentException illegalArgumentException) {
        log.info("Unable to get JWT token");
      } catch (ExpiredJwtException expiredJwtException) {
        log.info("JWT Token has expired");
      }
    } else {
      log.warn("JWT Token does not begin with Bearer String");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (tokenHelper.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}
