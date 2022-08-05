package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncorprationCompanyRepository extends JpaRepository<Company, Long> {

}
