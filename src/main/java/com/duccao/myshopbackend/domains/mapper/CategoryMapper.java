package com.duccao.myshopbackend.domains.mapper;

import com.duccao.myshopbackend.domains.dto.CategoryDTO;
import com.duccao.myshopbackend.domains.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  CategoryDTO categoryEntityToCategoryDTO(CategoryEntity product);

  List<CategoryDTO> lstCategoryEntityToLstCategoryDTO(List<CategoryEntity> products);
}
