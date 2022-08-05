package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income,Long> {
    @Query(value ="select i from Income i where i.client.id=:id and i.isActive=true")
    Page<Income> findAllByClientId(Long id, Pageable page);

    @Query(value ="select i from Income i where i.client.id=:id and i.isActive=true")
    List<Income> findAllByClientIdWithout(Long id);

    @Modifying
    @Transactional
    @Query(value ="update Income i set i.isActive = 0 where i.id = :id ")
    void deleteIncomeById(Long id);

    @Query(value = "select * from income_new ci where ci.client_id=:client_id and ci.is_active=true" +
            " and (:currency is null or ci.currency_code like concat('%',:currency,'%') )" +
            " and (:incomeType is null or ci.income_type_id = :incomeType)" +
            " and (:incomeDate is null or ci.income_date >= :incomeDate)" +
            " and (:incomeEndDate is null or ci.income_date <= :incomeEndDate)"+
            " and (:search is null or ci.income_type_id like concat('%',:search,'%'))", nativeQuery = true)
    Page<Income> findAllByFilter(String currency, LocalDate incomeDate, LocalDate incomeEndDate, Long client_id, String incomeType, String search, Pageable page);

    @Query(value = "select * from income_new ci where ci.client_id=:client_id and ci.is_active=true " +
            " and (:currency is null or ci.currency_code like concat('%',:currency,'%') )" +
            " and (:incomeType is null or ci.income_type_id = :incomeType)" +
            " and (:incomeDate is null or ci.income_date >= :incomeDate)" +
            " and (:incomeEndDate is null or ci.income_date <= :incomeEndDate)" +
            " and (:search is null or ci.income_type_id like concat('%',:search,'%'))", nativeQuery = true)
    List<Income> findByFilterForExcel(String currency, LocalDate incomeDate, LocalDate incomeEndDate, Long client_id,String incomeType,String search);

    Income findByTaskId (Long taskId);
}
