package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.BankTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionDetailRepository extends JpaRepository<BankTransactionDetail, Long> {

    List<BankTransactionDetail> findAllByBankTransactionId(Long transactionID);
}
