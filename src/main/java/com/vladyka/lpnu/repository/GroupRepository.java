package com.vladyka.lpnu.repository;

import com.vladyka.lpnu.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByInstituteId(Long instituteId);

    Group findByAbbrAndInstituteId(String groupAbbr, Long instituteId);

    @Query("SELECT COUNT(*) FROM Group u")
    Integer findTotalCount();
}
