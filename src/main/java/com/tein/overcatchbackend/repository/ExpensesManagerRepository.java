package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExpensesManagerRepository extends JpaRepository<ExpensesType, Long> {

    @Query(value ="select * from expenses_type i " +
            "where i.is_active =1",
            nativeQuery = true)
    List<ExpensesType> findAllActive();

    @Modifying
    @Transactional
    @Query(value = "update ExpensesType b set b.isActive =0 " +
            "where b.id= :id ")
    void deleteExpensesType(Long id);

    @Query(value = "select count(i.cash_invoice_type_id) from overcatch.cash_invoice i " +
            "where  i.cash_invoice_type_id=:expensesType_id",
            nativeQuery = true)
    Integer controlExpensesForDelete(Long expensesType_id);

    @Query(value = "select e from expenses_type e " +
            "where :search is null or e.type like concat('%',:search,'%')" +
            "and e.is_active = true ",
            nativeQuery = true)
    ExpensesType findByFilter(String search);
}


