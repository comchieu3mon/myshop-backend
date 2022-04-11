package com.duccao.myshopbackend.domains.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Table(name = "category")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE)
public class CategoryEntity extends BaseTimestampEntity {

  @Column(name = "name")
  String name;

  @Column(name = "description")
  String description;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
  List<ProductEntity> products = new ArrayList<>();

  public void addProduct(ProductEntity product) {
    products.add(product);
    product.setCategory(this);
  }

  public void removeProduct(ProductEntity product) {
    products.remove(product);
    product.setCategory(null);
  }
}
