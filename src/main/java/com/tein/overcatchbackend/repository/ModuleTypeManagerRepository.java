package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.domain.model.ModuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ModuleTypeManagerRepository extends JpaRepository<ModuleType, Long>{

    @Query(value ="select * from module_type i " +
            "where i.is_active =1 or i.is_active is null",
            nativeQuery = true)
    List<ModuleType> findAllActive();

    @Query(value ="select * from module_type i " +
            "where (i.is_active =1 or i.is_active is null) and i.id = :moduleId",
            nativeQuery = true)
    ModuleType getModuleTypeId(Long moduleId);

}
