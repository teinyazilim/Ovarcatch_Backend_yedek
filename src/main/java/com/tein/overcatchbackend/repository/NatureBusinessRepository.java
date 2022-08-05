package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.NatureBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatureBusinessRepository extends JpaRepository<NatureBusiness, Long> {
    NatureBusiness findByCode(String code);

}
