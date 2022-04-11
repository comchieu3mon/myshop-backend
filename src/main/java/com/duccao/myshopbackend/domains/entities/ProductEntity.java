package com.duccao.myshopbackend.domains.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "product")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends BaseTimestampEntity {
  String name;

  double price;

  String description;

  boolean availability;

  String imagePath;

  @ManyToOne(fetch = FetchType.LAZY)
  CategoryEntity category;
}
