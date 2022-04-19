package com.duccao.myshopbackend.controller;

import com.duccao.myshopbackend.domains.dto.CategoryDTO;
import com.duccao.myshopbackend.services.CategoryService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryController {
  @NonNull CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> findAll() {
    return ResponseEntity.ok().body(categoryService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> findById(@PathVariable(name = "id") String id) {
    return ResponseEntity.ok().body(categoryService.findById(UUID.fromString(id)));
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable(name = "id") String id) {
    categoryService.deleteCategory(UUID.fromString(id));
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
    return ResponseEntity.ok().body(categoryService.create(categoryDTO));
  }
}
