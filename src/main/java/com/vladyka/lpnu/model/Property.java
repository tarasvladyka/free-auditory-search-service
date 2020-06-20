package com.vladyka.lpnu.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "property")
public class Property {

  @Id
  private String code;

  @Column(nullable = false)
  private String value;

  @Column(nullable = false)
  private LocalDateTime updatedOn;

  public String getCode() {
    return code;
  }

  public Property setCode(String code) {
    this.code = code;
    return this;
  }

  public String getValue() {
    return value;
  }

  public Property setValue(String value) {
    this.value = value;
    return this;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public Property setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
    return this;
  }

  @PreUpdate
  private void prePersist() {
    updatedOn = LocalDateTime.now();
  }
}