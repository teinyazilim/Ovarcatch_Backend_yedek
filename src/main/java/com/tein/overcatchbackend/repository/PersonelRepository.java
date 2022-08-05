package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.Personel;
import com.tein.overcatchbackend.domain.model.TaskConfirmation;
import com.tein.overcatchbackend.domain.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonelRepository extends JpaRepository<Personel, Long> {

    @Query(value ="select * from personel i " +
            "join user u on u.id = i.user_id  " +
            "where i.is_active is null or i.is_active =1 ",
            nativeQuery = true)
    List<Personel> findPersonelName();

    @Query(value = "select * from tasks t, user u " +
            "left join tasks_confirmation tc on tc.task_id = t.id " +
            "join user u on u.id = t.user_id  " +
            "where t.id = :taskId ",
            nativeQuery = true)
            List<TaskConfirmation> findTasks();
}
