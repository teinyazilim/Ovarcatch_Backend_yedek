package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.dto.CompanyDTO;
import com.tein.overcatchbackend.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "select * from company c where c.client_id = :clientId",
            nativeQuery = true)
    List<CompanyDTO> findByClientId(long clientId);
}
