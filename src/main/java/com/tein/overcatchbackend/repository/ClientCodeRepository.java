package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.ClientCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientCodeRepository extends JpaRepository<ClientCode, Long> {


    public ClientCode findBySeqName(String seqName);
}
