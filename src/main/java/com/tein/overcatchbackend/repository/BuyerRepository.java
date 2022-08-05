package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Buyer;
import com.tein.overcatchbackend.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    @Query(value ="select * from buyer_detail b " +
            "where b.client_id=:id and b.is_active =1",
            nativeQuery = true)
    List<Buyer> findAllByClientId(Long id);


    @Modifying
    @Transactional
    @Query(value ="update Buyer b set b.isActive =0 " +
            "where b.id= :id ")
    void deleteBuyerById(Long id);

    @Query(value = "select count(i.buyer_name) from overcatch.invoice i where i.client_id=:client_id and i.buyer_name=:buyer_name",
            nativeQuery = true)
    Integer controlForDeleteCustomer(Long client_id, String buyer_name);
}