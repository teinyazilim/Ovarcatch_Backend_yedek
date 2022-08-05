package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.ReminderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderTypeRepository extends JpaRepository<ReminderType, Long> {
    ReminderType findById(ReminderType reminderType);

}
