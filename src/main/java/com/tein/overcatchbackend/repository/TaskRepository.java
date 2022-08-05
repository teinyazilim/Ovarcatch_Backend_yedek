package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.domain.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.stream.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository  extends JpaRepository<Tasks, Long> {

    @Query(value = "select * from tasks t " +
            "left join company c on c.id = t.company_id " +
            "left join user u on u.id = t.user_id  " +
            "left join tasks_confirmation tc on tc.task_id = t.id " +
            "where t.id = :taskId ",
            nativeQuery = true)
    Optional<Tasks> findByTaskId(long taskId);

    @Query(value = "select * from tasks t " +
            "left join company c on c.id = t.company_id " +
            "left join user u on u.id = t.user_id  " +
            "left join tasks_confirmation tc on tc.task_id = t.id",
            nativeQuery = true)
    List<Tasks> findAllTaskList();

    List<Tasks> findAllByClientId(Long id);


}
