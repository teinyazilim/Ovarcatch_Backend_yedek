package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.AddressNew;
import com.tein.overcatchbackend.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressNewRepository extends JpaRepository<AddressNew, Long> {

    @Query(value = "select * from addressnew c where c.client_address_id = :clientId order by c.insert_time desc LIMIT 6",
            nativeQuery = true)
    List<AddressNew> getNewAddressList(Long clientId);
    //Yukarıdaki Kodu Yeni ekledim.
}