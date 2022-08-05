package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.IncomeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {

    @Query(value ="select * from income_type i " +
            "where i.is_active =1",
            nativeQuery = true)
    List<IncomeType> findAllActive();

    @Modifying
    @Transactional
    @Query(value = "update IncomeType b set b.isActive =0 " +
            "where b.id= :id ")
    void deleteIncomesType(Long id);

    @Query(value = "select count(i.income_type_id) from overcatch.income_new i " +
            "where  i.income_type_id=:incomeType_id",
            nativeQuery = true)
    Integer controlIncomesForDelete(Long incomeType_id);

}
