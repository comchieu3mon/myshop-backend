package com.duccao.myshopbackend.domains.mapper;

import com.duccao.myshopbackend.domains.dto.RegisteredUserDTO;
import com.duccao.myshopbackend.domains.dto.UserDTO;
import com.duccao.myshopbackend.domains.entities.Authority;
import com.duccao.myshopbackend.domains.entities.UserEntity;
import com.duccao.myshopbackend.domains.enums.AuthorityName;
import java.util.function.Function;

public class Mapper {
  private static Function<RegisteredUserDTO, UserEntity> registeredUserDTOToUserEntity =
      registeredUser -> {
        UserEntity user = new UserEntity();
        user.setEmail(registeredUser.getEmail());
        user.setPassword(registeredUser.getPassword());
        user.setFirstName(registeredUser.getFirstName());
        user.setLastName(registeredUser.getLastName());
        user.setActive(true);
        registeredUser
            .getAuthorities()
            .forEach(
                name -> {
                  Authority authority = new Authority();
                  authority.setName(AuthorityName.valueOf(name));
                  user.getRoles().add(authority);
                });
        return user;
      };

  private static Function<UserEntity, RegisteredUserDTO> userEntityToRegisteredUserDTO =
      userEntity -> {
        RegisteredUserDTO registeredUser = new RegisteredUserDTO();
        registeredUser.setId(userEntity.getId());
        registeredUser.setEmail(userEntity.getEmail());
        return registeredUser;
      };

  private static Function<UserEntity, UserDTO> userEntityToUserDTO =
      userEntity ->
          new UserDTO(
              userEntity.getId(),
              userEntity.getEmail(),
              userEntity.getFirstName(),
              userEntity.getLastName(),
              userEntity.getActive());

  public static UserEntity from(RegisteredUserDTO userRequest) {
    return registeredUserDTOToUserEntity.apply(userRequest);
  }

  public static RegisteredUserDTO from(UserEntity user) {
    return userEntityToRegisteredUserDTO.apply(user);
  }

  public static UserDTO convert(UserEntity user) {
    return userEntityToUserDTO.apply(user);
  }
}
