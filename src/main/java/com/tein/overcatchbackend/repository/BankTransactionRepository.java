package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    List<BankTransaction> findAllByClientId(Long id);

    BankTransaction findByClientId(Long id);
}
