package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.ModuleTypesResponse;
import com.tein.overcatchbackend.domain.dto.TaskConfirmationDTO;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.TaskConfirmation;
import com.tein.overcatchbackend.domain.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskConfirmationRepository extends JpaRepository<TaskConfirmation, Long> {
//    @Query(value = "select t.id , tc.confirm_type as confirmation,count(*) as deger ,t.module_type as moduleType  from tasks t" +
//            " left join (select max(id) max_id,task_id" +
//            " from tasks_confirmation" +
//            " group by task_id) t_max on (t_max.task_id=t.id)" +
//            " left join tasks_confirmation tc ON (tc.id = t_max.max_id)" +
//            " group by tc.confirm_type, t.module_type ",
//            nativeQuery = true)
//    List<ModuleTypesResponse> findAllModuleTypes();

    @Query(value = "select * from tasks_confirmation tc " +
            "where tc.personel_id = :Id  ",
            nativeQuery = true)
    List<TaskConfirmation> findById(long Id);

    @Query(value = "select *,  DATEDIFF(insert_time, update_time) as process_time from tasks_confirmation tc where tc.confirm_type = 'INPROGRESS'", nativeQuery = true)
    List<TaskConfirmation> findInProgress();

    @Query(value = "select *,  DATEDIFF(insert_time, update_time) as process_time from tasks_confirmation tc where tc.confirm_type = 'DONE'", nativeQuery = true)
    List<TaskConfirmation> findDone();
}
