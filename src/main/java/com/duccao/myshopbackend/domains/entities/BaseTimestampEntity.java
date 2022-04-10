package com.duccao.myshopbackend.domains.entities;

import static lombok.AccessLevel.PROTECTED;

import com.google.common.collect.ComparisonChain;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@FieldDefaults(level = PROTECTED)
@Getter
@Setter
public abstract class BaseTimestampEntity implements Serializable, Comparable<BaseTimestampEntity> {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  UUID id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "timestamp_created", nullable = false)
  Calendar timestampCreated;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "timestamp_modified", nullable = false)
  Calendar timestampModified;

  @PrePersist
  protected void onCreate() {
    timestampCreated = Calendar.getInstance();
    timestampModified = Calendar.getInstance();
  }

  @PreUpdate
  protected void onUpdate() {
    timestampModified = Calendar.getInstance();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || this.getClass() != obj.getClass()) {
      return false;
    }
    BaseTimestampEntity that = (BaseTimestampEntity) obj;
    return Objects.equals(timestampCreated, that.timestampCreated)
        && Objects.equals(timestampModified, that.timestampModified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestampCreated, timestampModified);
  }

  @Override
  public int compareTo(BaseTimestampEntity o) {
    if (o.timestampCreated == null || o.timestampModified == null) {
      return -1;
    }
    return ComparisonChain.start()
        .compare(o.timestampCreated, timestampCreated)
        .compare(o.timestampModified, timestampModified)
        .result();
  }
}
