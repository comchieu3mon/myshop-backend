package com.duccao.myshopbackend.controller;

import com.duccao.myshopbackend.domains.dto.ProductDTO;
import com.duccao.myshopbackend.services.ProductService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * The {@link ProductController ProductController} provides APIs for allocation logic
 *
 * @author duccaom
 * @version 1.0
 * @since 2022/04/19
 */
@RestController
@RequestMapping("/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductController {

  @NonNull ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductDTO>> findAll() {
    return productService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable(name = "id") String id) {
    return productService.findById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable(name = "id") String id) {
    productService.deleteById(id);
  }

  @PostMapping
  public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
    return productService.create(productDTO);
  }
}
