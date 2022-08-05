package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.CustomerClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerClientRepository extends JpaRepository<CustomerClient, Long> {
    @Query(value = "select * from customer_client cc " +
            "left join client c on c.id = cc.client_id " +
            "where cc.customer_id = :customerId and c.is_active=1",
            nativeQuery = true)
    List<CustomerClient> findAllByCustomerId(Long customerId);
}
