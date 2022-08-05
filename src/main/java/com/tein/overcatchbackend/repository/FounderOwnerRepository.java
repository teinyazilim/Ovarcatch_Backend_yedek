package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.FounderOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FounderOwnerRepository extends JpaRepository<FounderOwner, Long> {

    @Query(value = "select * from founder_owner f " +
            "left join client c on c.id = f.client_id " +
            "where f.client_id = :clientId",
            nativeQuery = true)
    List<FounderOwner> findByClientId(long clientId);
}
