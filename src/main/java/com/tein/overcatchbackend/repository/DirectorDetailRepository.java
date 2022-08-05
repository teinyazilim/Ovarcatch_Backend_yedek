package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.DirectorDetail;
import com.tein.overcatchbackend.domain.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorDetailRepository extends JpaRepository<DirectorDetail, Long> {

    DirectorDetail findByName(String name);

    @Query(value = "select * from director_detail dd where dd.id= :id", nativeQuery = true)
    DirectorDetail findByDirectorId(Long id);

    @Query(value = "select * from director_detail dd where (dd.id in :directorDetailList)",nativeQuery = true)
    List<DirectorDetail> findAllByListDirectorId(List<Integer> directorDetailList);
}
