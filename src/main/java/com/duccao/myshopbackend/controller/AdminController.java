package com.duccao.myshopbackend.controller;

import com.duccao.myshopbackend.domains.dto.AuthenticationDTO;
import com.duccao.myshopbackend.domains.dto.CredentialDTO;
import com.duccao.myshopbackend.services.AuthenticationService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AdminController {

  @NonNull AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationDTO> adminAuthenticate(
      @RequestBody CredentialDTO credential) {
    AuthenticationDTO res = authenticationService.authenticateAdmin(credential);
    return ResponseEntity.ok().body(res);
  }
}
