package com.duccao.myshopbackend.services;

import com.duccao.myshopbackend.domains.dto.AuthenticationDTO;
import com.duccao.myshopbackend.domains.dto.CredentialDTO;
import com.duccao.myshopbackend.domains.entities.UserEntity;
import com.duccao.myshopbackend.utils.TokenHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.duccao.myshopbackend.domains.common.CommonConstants.ADMIN_USERNAME;
import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthenticationService {

  @NonNull AuthenticationManager authenticationManager;
  @NonNull TokenHelper tokenHelper;

  public AuthenticationDTO authenticate(CredentialDTO credential) {

    // Wont allow Admin user login as Normal user flow
    if (ADMIN_USERNAME.equalsIgnoreCase(credential.getUsername())) {
      throw new BadCredentialsException("Invalid credential.");
    }

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                credential.getUsername(), credential.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserEntity user = (UserEntity) authentication.getPrincipal();
    final String token = tokenHelper.generateToken(user.getUsername());
    return AuthenticationDTO.builder().token(token).expiredIn(tokenHelper.getExpiresIn()).build();
  }

  public AuthenticationDTO authenticateAdmin(CredentialDTO credential) {

    // Only allow Admin user login using this method
    if (!ADMIN_USERNAME.equalsIgnoreCase(credential.getUsername())) {
      throw new BadCredentialsException("Invalid credential.");
    }
    final String token = tokenHelper.generateAdminToken(credential.getUsername());
    return AuthenticationDTO.builder().token(token).expiredIn(tokenHelper.getExpiresIn()).build();
  }
}
