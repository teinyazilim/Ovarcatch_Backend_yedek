package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    @Query(value = "select * from bank b " +
            "where b.client_id=:clientId and b.is_active =1",
            nativeQuery = true)
    List<Bank> findAllByClientId(Long clientId);


    @Modifying
    @Transactional
    @Query(value = "update Bank b set b.isActive =0 " +
            "where b.id= :id ")
    void deleteBuyerById(Long id);

    @Query(value = "select count(i.bank_id) from overcatch.invoice i where i.client_id=:clientId and i.bank_id=:bank_id",
            nativeQuery = true)
    Integer controlBankForDelete(Long clientId, Long bank_id);
}