package com.duccao.myshopbackend.domains.entities;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.duccao.myshopbackend.domains.enums.AuthorityName;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
@Table(name = "authority")
public class Authority extends BaseTimestampEntity implements GrantedAuthority {
  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  AuthorityName name;

  @Override
  public String getAuthority() {
    return name.name();
  }
}
