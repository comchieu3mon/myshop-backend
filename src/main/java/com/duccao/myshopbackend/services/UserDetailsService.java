package com.duccao.myshopbackend.services;

import static lombok.AccessLevel.PRIVATE;

import com.duccao.myshopbackend.domains.entities.UserEntity;
import com.duccao.myshopbackend.repository.UserRepository;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  @NonNull UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> userEntityOptional = userRepository.findByEmailIgnoreCase(username);
    if (userEntityOptional.isEmpty()) {
      throw new UsernameNotFoundException("Username not found!");
    }
    return userEntityOptional.get();
  }
}
