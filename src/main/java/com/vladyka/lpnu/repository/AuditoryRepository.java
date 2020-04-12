package com.vladyka.lpnu.repository;

import com.vladyka.lpnu.model.Auditory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoryRepository extends JpaRepository<Auditory, Long> {

    Auditory getByNameAndCampusId(String auditoryName, Long campusId);
}
