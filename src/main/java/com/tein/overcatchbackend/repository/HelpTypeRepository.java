package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.domain.model.HelpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpTypeRepository extends JpaRepository<HelpType, Long> {

    HelpType findById(HelpType helpType);

}
