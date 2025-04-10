package com.codemonkey.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

  /**
   * @MappedSuperclass - to inform spring data jpa that this is a super class and extending entities
   * will have fields.
   * @Column - we can mention columns names, but by defualt column names will be created using snake
   * case by replacing came case.
   */

  @Column(updatable = false)
  private LocalDateTime createdAt;

  @Column(updatable = false)
  private String createdBy;

  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @Column(insertable = false)
  private String updatedBy;

}
