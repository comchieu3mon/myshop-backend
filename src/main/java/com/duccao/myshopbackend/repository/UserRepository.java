package com.duccao.myshopbackend.repository;

import com.duccao.myshopbackend.domains.entities.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByEmailIgnoreCase(String email);

  List<UserEntity> findByActive(boolean active);
}
