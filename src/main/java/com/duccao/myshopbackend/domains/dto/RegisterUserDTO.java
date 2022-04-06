package com.duccao.myshopbackend.domains.dto;

import com.duccao.myshopbackend.domains.common.RegexConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
  private UUID id;

  @Email(message = "Email must be valid and less than 256 characters")
  @Length(max = 255, message = "Email must not have more than 255 characters")
  private String email;

  @NotBlank(message = "Password must not be blank")
  @Length(
      min = 8,
      max = 128,
      message =
          "Password must have at least 8 characters and less than 129 characters with a mix of letters, numbers, and symbols.")
  @Pattern(
      regexp = RegexConstant.PASSWORD_PATTERN,
      message = "Password must contain a mix of letters, numbers and symbols")
  private String password;

  @NotBlank(message = "Confirm password must not be blank")
  private String confirmedPassword;

  @NotBlank(message = "First name must not be blank")
  @Length(
      max = 255,
      message = "First Name must have at least 1 character and less than 256 characters")
  private String firstName;

  @NotBlank(message = "Last name must not be blank")
  @Length(
      max = 255,
      message = "Last Name must have at least 1 character and less than 256 characters")
  private String lastName;

  List<String> authorities = new ArrayList<>();
}
