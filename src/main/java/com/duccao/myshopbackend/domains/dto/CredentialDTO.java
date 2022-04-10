package com.duccao.myshopbackend.domains.dto;

import static lombok.AccessLevel.PRIVATE;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CredentialDTO {
  @NotBlank(message = "Username cannot be blank")
  @Email(message = "Email should be valid")
  String username;

  @NotBlank(message = "Password cannot be blank")
  String password;
}
