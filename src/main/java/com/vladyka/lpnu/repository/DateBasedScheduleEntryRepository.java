package com.vladyka.lpnu.repository;

import com.vladyka.lpnu.model.DateBasedScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateBasedScheduleEntryRepository extends JpaRepository<DateBasedScheduleEntry, Long> {

}
