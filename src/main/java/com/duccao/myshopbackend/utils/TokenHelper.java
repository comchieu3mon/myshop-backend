package com.duccao.myshopbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenHelper {
  @Value("${jwt.expiresIn}")
  private long expiresIn;

  @Value("${jwt.issuer}")
  private String issuer;

  @Value("${jwt.secret")
  private String secret;

  public String generateToken(String username) {
    return Jwts.builder()
        .setIssuer(issuer)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(new Date().getTime() + expiresIn * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public String generateAdminToken(String username) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("scopes", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

    return Jwts.builder()
        .setClaims(claims)
        .setIssuer(issuer)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(new Date().getTime() + expiresIn * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    if (!userDetails.isEnabled()) {
      throw new DisabledException("User is deactivated");
    }
    String username = getUsernameFromToken(token);
    return (username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public long getExpiresIn() {
    return expiresIn;
  }
}
