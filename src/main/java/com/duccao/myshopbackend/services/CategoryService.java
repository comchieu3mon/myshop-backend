package com.duccao.myshopbackend.services;

import com.duccao.myshopbackend.domains.dto.CategoryDTO;
import com.duccao.myshopbackend.domains.entities.CategoryEntity;
import com.duccao.myshopbackend.domains.mapper.CategoryMapper;
import com.duccao.myshopbackend.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService {

  @NonNull CategoryRepository categoryRepository;

  public CategoryDTO findById(UUID id) {
    CategoryEntity category =
        categoryRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(String.format("No such category with id: %s", id)));
    return CategoryMapper.INSTANCE.categoryEntityToCategoryDTO(category);
  }

  public List<CategoryDTO> findAll() {
    return CategoryMapper.INSTANCE.lstCategoryEntityToLstCategoryDTO(categoryRepository.findAll());
  }

  public CategoryDTO findByName(String name) {
    CategoryEntity category =
        categoryRepository
            .findByName(name)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("No such category with name: %s", name)));
    return CategoryMapper.INSTANCE.categoryEntityToCategoryDTO(category);
  }

  public void deleteCategory(UUID id) {
    categoryRepository.deleteById(id);
  }

  public CategoryDTO create(CategoryDTO categoryDTO) {
    return CategoryMapper.INSTANCE.categoryEntityToCategoryDTO(
        categoryRepository.save(CategoryMapper.INSTANCE.categoryDtoToCategoryEntity(categoryDTO)));
  }
}
