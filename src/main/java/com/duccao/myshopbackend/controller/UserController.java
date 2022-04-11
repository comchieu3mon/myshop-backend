package com.duccao.myshopbackend.controller;

import com.duccao.myshopbackend.domains.dto.RegisteredUserDTO;
import com.duccao.myshopbackend.domains.dto.UserDTO;
import com.duccao.myshopbackend.exception.InvalidRequestException;
import com.duccao.myshopbackend.services.UserService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

  @NonNull UserService userService;

  @PostMapping(value = "/register")
  public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisteredUserDTO registeredUserDTO)
      throws InvalidRequestException {
    UserDTO res = userService.create(registeredUserDTO);
    return ResponseEntity.ok().body(res);
  }


}
