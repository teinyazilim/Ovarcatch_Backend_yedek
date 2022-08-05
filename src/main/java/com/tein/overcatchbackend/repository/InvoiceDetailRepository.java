package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

//    @Query(value ="select * from invoice i " +
//            "where i.client_id=:id and i.is_active =1",
//            nativeQuery = true)
//    List<Invoice> findAllByClientId(Long id);
//
//
//    @Modifying
//    @Transactional
//    @Query(value ="update Invoice i set i.isActive =0 " +
//            "where i.id= :id ")
//    void deleteInvoiceById(Long id);


    List<InvoiceDetail> findAllByInvoiceId(Long id);
}