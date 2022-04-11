package com.duccao.myshopbackend.domains.dto;


import com.duccao.myshopbackend.domains.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    UUID id;
    double price;
    String description;
    boolean availability;
    String imagePath;
    CategoryDTO category;
}
