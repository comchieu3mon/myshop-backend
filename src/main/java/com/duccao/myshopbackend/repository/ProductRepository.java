package com.duccao.myshopbackend.repository;

import com.duccao.myshopbackend.domains.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findById(UUID id);
    List<ProductEntity> findByCategory_Id(UUID categoryId);
}
