package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.ModuleTypesResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskModuleTypesRepository extends JpaRepository<ModuleTypesResponse, Long> {
/*count - 1 vardı*/
    @Query(value = " select mt2.id as id, count(tc.confirm_type) as confirmation,(count(*)) as deger,mt2.module_type_enum  from tasks t"+
                   " left join (select max(id) max_id,task_id from tasks_confirmation group by task_id) t_max on (t_max.task_id=t.id)"+
                   " left join tasks_confirmation tc ON (tc.id = t_max.max_id)"+
                   " left join module_type mt on mt.id= t.module_type"+ /*right idi*/
                   " left join module_type mt2 on mt.module_type_id= mt2.id"+
                   " group by mt2.module_type_enum ",
            nativeQuery = true)
    List<ModuleTypesResponse> findAllModuleTypes();
}
