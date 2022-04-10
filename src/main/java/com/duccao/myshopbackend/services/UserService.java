package com.duccao.myshopbackend.services;

import static lombok.AccessLevel.PRIVATE;

import com.duccao.myshopbackend.domains.dto.RegisteredUserDTO;
import com.duccao.myshopbackend.domains.dto.UserDTO;
import com.duccao.myshopbackend.domains.entities.Authority;
import com.duccao.myshopbackend.domains.entities.UserEntity;
import com.duccao.myshopbackend.domains.mapper.Mapper;
import com.duccao.myshopbackend.exception.InvalidRequestException;
import com.duccao.myshopbackend.exception.UserAlreadyExistException;
import com.duccao.myshopbackend.repository.AuthorityRepository;
import com.duccao.myshopbackend.repository.UserRepository;
import com.duccao.myshopbackend.utils.TokenHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = PRIVATE)
@RequiredArgsConstructor
public class UserService {

  @NonNull UserRepository userRepository;
  @NonNull AuthorityRepository authorityRepository;
  @Lazy @NonNull PasswordEncoder passwordEncoder;
  @Lazy @NonNull TokenHelper tokenHelper;

  public RegisteredUserDTO create(RegisteredUserDTO registeredUser)
      throws UserAlreadyExistException, InvalidRequestException {
    log.info("Register User Begin, UserDTO: {}", registeredUser);
    if (emailExists(registeredUser.getEmail())) {
      throw new UserAlreadyExistException(
          "There is an existing account associated with this email");
    }

    if (!registeredUser.getPassword().equals(registeredUser.getConfirmedPassword())) {
      throw new InvalidRequestException("Confirmed Password must be the same as Password");
    }

    UserEntity user = Mapper.from(registeredUser);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    List<Authority> authorities = authorityRepository.findAll();
    try {
      if (user.getRoles().isEmpty()) {
        user.setAuthorities(
            authorities.stream()
                .filter(authority -> "ROLE_USER".equals(authority.getName().name()))
                .collect(Collectors.toList()));
      } else {
        List<Authority> matchesAuthorities = findMatchesAuthorities(user.getRoles(), authorities);
        user.getAuthorities().clear();
        user.setAuthorities(matchesAuthorities);
      }

      userRepository.save(user);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return Mapper.from(user);
  }

  public Authentication getLoggedUser() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public List<UserDTO> findAll() {
    List<UserEntity> users = userRepository.findAll();
    return users.stream()
        // Force to remove "admin" user out of the user list
        .filter(user -> !AuthenticationService.ADMIN_USERNAME.equalsIgnoreCase(user.getUsername()))
        .sorted(Comparator.comparing(UserEntity::getUsername, String.CASE_INSENSITIVE_ORDER))
        .map(Mapper::convert)
        .toList();
  }

  private List<Authority> findMatchesAuthorities(
      List<Authority> firstAuthorities, List<Authority> secondAuthorities) {
    List<Authority> currentAuthorities = new ArrayList<>();
    for (Authority authority : firstAuthorities) {
      Authority existedAuthority =
          findAuthorityByName(secondAuthorities, authority.getName().name());
      if (existedAuthority != null) {
        currentAuthorities.add(existedAuthority);
      }
    }
    return currentAuthorities;
  }

  private Authority findAuthorityByName(List<Authority> authorities, String name) {
    for (Authority authority : authorities) {
      if (name.equals(authority.getName().name())) {
        return authority;
      }
    }
    return null;
  }

  private boolean emailExists(String email) {
    return userRepository.findByEmailIgnoreCase(email).isPresent();
  }

  private UserEntity getUserFromOptional(String userId) throws InvalidRequestException {
    var user = userRepository.findById(UUID.fromString(userId));
    if (user.isEmpty()) {
      throw new InvalidRequestException(String.format("User (%s) doesn't exist", userId));
    }
    return user.get();
  }
}
