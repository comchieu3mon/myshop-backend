package com.duccao.myshopbackend.services;

import com.duccao.myshopbackend.domains.dto.ProductDTO;
import com.duccao.myshopbackend.domains.entities.CategoryEntity;
import com.duccao.myshopbackend.domains.entities.ProductEntity;
import com.duccao.myshopbackend.domains.mapper.ProductMapper;
import com.duccao.myshopbackend.repository.CategoryRepository;
import com.duccao.myshopbackend.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author duccaom
 * @version 1.0
 * @since 2022/04/19
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductService {
  @NonNull ProductRepository productRepository;
  @NonNull CategoryRepository categoryRepository;

  public ResponseEntity<ProductDTO> findById(String id) {
    return ResponseEntity.ok(
        ProductMapper.INSTANCE.productEntityToProductDTO(
            productRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                    () ->
                        new EntityNotFoundException(
                            String.format("Product with id: {%s} cannot be found", id)))));
  }

  public ResponseEntity<List<ProductDTO>> findAll() {
    return ResponseEntity.ok(
        ProductMapper.INSTANCE.lstProductEntityToLstProductDTO(productRepository.findAll()));
  }

  public void deleteById(String id) {
    productRepository.deleteById(UUID.fromString(id));
  }

  public ResponseEntity<ProductDTO> create(ProductDTO productDTO) {
    // category chua co thi tao
    // co roi thi find ra, add product vao roi save
    ProductEntity product = ProductMapper.INSTANCE.productDtoToProductEntity(productDTO);
    Optional<CategoryEntity> categoryEntityOptional =
        categoryRepository.findByName(productDTO.getCategory().getName());
    if (categoryEntityOptional.isPresent()) {
      CategoryEntity category = categoryEntityOptional.get();
      category.addProduct(product);
      categoryRepository.save(category);
    }
    CategoryEntity newCategory =
        CategoryEntity.builder()
            .description(productDTO.getCategory().getDescription())
            .name(productDTO.getCategory().getName())
            .build();
    newCategory.addProduct(product);
    categoryRepository.save(newCategory);
    return ResponseEntity.ok(productDTO);
  }
}
