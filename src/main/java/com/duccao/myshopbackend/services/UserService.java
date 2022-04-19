package com.duccao.myshopbackend.services;

import com.duccao.myshopbackend.domains.dto.RegisteredUserDTO;
import com.duccao.myshopbackend.domains.dto.UserDTO;
import com.duccao.myshopbackend.domains.entities.Authority;
import com.duccao.myshopbackend.domains.entities.UserEntity;
import com.duccao.myshopbackend.domains.mapper.Mapper;
import com.duccao.myshopbackend.exception.InvalidRequestException;
import com.duccao.myshopbackend.exception.UserAlreadyExistException;
import com.duccao.myshopbackend.repository.AuthorityRepository;
import com.duccao.myshopbackend.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.duccao.myshopbackend.domains.common.CommonConstants.ADMIN_USERNAME;
import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

  @NonNull UserRepository userRepository;
  @NonNull AuthorityRepository authorityRepository;
  @Lazy @NonNull PasswordEncoder passwordEncoder;

  public UserDTO create(RegisteredUserDTO registeredUser)
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

    return Mapper.convert(user);
  }

  public Authentication getLoggedUser() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public List<UserDTO> findAll() {
    List<UserEntity> users = userRepository.findAll();
    return users.stream()
        // Force to remove "admin" user out of the user list
        .filter(user -> !ADMIN_USERNAME.equalsIgnoreCase(user.getUsername()))
        .sorted(Comparator.comparing(UserEntity::getUsername, String.CASE_INSENSITIVE_ORDER))
        .map(Mapper::convert)
        .collect(Collectors.toList());
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

  public UserDTO findById(String id) {
    return Mapper.convert(
        userRepository
            .findById(UUID.fromString(id))
            .orElseThrow(
                () ->
                    new EntityNotFoundException(String.format("User with id: %s not found!", id))));
  }
}
