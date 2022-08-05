package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Divided;
import com.tein.overcatchbackend.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividedRepository extends JpaRepository<Divided, Long> {

    @Query(value ="select * from divided d " +
            "where d.director_id=:directorId and d.is_active =1 order by d.insert_time desc ",
            nativeQuery = true)
    List<Divided> findAllByDirectorId(Long directorId);

    @Query(value=" select * from divided d where id = (Select max(id) from divided where director_id = :directorId)", nativeQuery = true)
    Divided findDividedBy(Long directorId);

    @Query(value ="select director_id from divided d" ,nativeQuery = true)
    List<Integer> getAllDirectors();

    @Query(value = "SELECT * From divided d where director_id in (SELECT distinct director_id from divided) group by director_id", nativeQuery = true)
    List<Divided> getAllLastDivided();

}
