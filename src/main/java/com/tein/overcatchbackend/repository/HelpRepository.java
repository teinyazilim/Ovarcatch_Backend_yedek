package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.domain.model.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpRepository extends JpaRepository<Help, Long> {

    Help findById(Help help);

    Help findByTaskId(Long id);

    List<Help> findAllByClientId(Long id);

    @Query(value = "SELECT * FROM help hlp "
            +"left join help_type ht on hlp.help_type_id = ht.id "
            +"join tasks t  on hlp.task_id = t.id "
            +"left join tasks_confirmation tc on t.id = tc.task_id "
            +"join user u  on t.user_id = u.id "
            +"join client c on c.id = t.client_id "
            +"left join company cy on cy.client_id = c.id "
            +"where ( (ht.help_type_show_name like concat('%',:search,'%')) "
            +"or (u.name like concat('%',:search,'%') or u.surname like concat('%',:search,'%')) "
            +"or (tc.confirm_type like concat('%',:search,'%')) "
            +"or (cy.name like concat('%',:search,'%')))",nativeQuery = true)
    List<Help> findSupport(String search);

    @Query(value = "SELECT * FROM help hlp "
                +"left join help_type ht on hlp.help_type_id = ht.id "
                +"join tasks t on hlp.task_id = t.id "
                +"join user u on t.user_id = u.id "
                +"join client c on c.id = t.client_id "
                +"left join company cy on cy.client_id = c.id "
                +"left join ( select max(id) max_id,task_id from tasks_confirmation group by task_id) t_max on (t_max.task_id= hlp.task_id) "
                +"left join tasks_confirmation tc ON (tc.id = t_max.max_id) "
                +"where ( (cy.name like concat('%',:search,'%') or u.name like concat('%',:search,'%') or u.surname like concat('%',:search,'%') ) "
                +"and   ( ht.help_type_show_name IN :supportType)"
                +"and   ( coalesce(tc.confirm_type, 'PENDING') IN :statusType ) ) order by hlp.insert_time desc "
                ,nativeQuery = true)
    List<Help> findSupportFilter(String search ,List<String> supportType, List<String> statusType);

    @Query(value = "SELECT * FROM overcatch.help h left join tasks_confirmation tc on h.task_id = tc.task_id where tc.confirm_type != 'DONE' OR tc.confirm_type is null",nativeQuery = true)
    List<Help> findAllHelp();
}
