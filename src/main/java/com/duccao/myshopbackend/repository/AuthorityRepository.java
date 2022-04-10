package com.duccao.myshopbackend.repository;

import com.duccao.myshopbackend.domains.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {}
