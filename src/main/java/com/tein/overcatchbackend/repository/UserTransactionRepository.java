package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {

}
