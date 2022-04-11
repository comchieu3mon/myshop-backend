package com.duccao.myshopbackend.domains.mapper;

import com.duccao.myshopbackend.domains.dto.ProductDTO;
import com.duccao.myshopbackend.domains.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category", target = "category")
    ProductDTO productEntityToProductDTO(ProductEntity product);

    List<ProductDTO> lstProductEntityToLstProductDTO(List<ProductEntity> products);
}
