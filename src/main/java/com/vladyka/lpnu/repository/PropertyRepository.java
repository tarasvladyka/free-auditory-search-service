package com.vladyka.lpnu.repository;

import com.vladyka.lpnu.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

  Property getByCode(String code);

  @Transactional
  @Modifying
  @Query("update Property p set p.value = :value, p.updatedOn = CURRENT_TIMESTAMP WHERE p.code = :code")
  void updateByCode(String code, String value);
}
