package com.tein.overcatchbackend.repository;


import com.tein.overcatchbackend.domain.model.Buyer;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Invoice;
import com.tein.overcatchbackend.enums.InvoiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value ="select * from invoice i " +
            "where i.client_id=:id and i.is_active =1 order by i.insert_time desc ",
            nativeQuery = true)
    List<Invoice> findAllByClientId(Long id);

    @Query(value ="select * from invoice i left join invoice_tasks it on i.id=it.invoice_id left join " +
            "tasks t on t.id=it.task_id where t.id=:id and i.is_active =1",
            nativeQuery = true)
    Invoice findByTaskId(Long id);



    @Query(value ="select * from invoice i " +
            "where i.id=:id and i.is_active =1",
            nativeQuery = true)
    Invoice findByInvoiceId(Long id);

    @Modifying
    @Transactional
    @Query(value ="update Invoice i set i.isActive =0 " +
            "where i.id= :id ")
    void deleteInvoiceById(Long id);

//    @Query(value ="select * from invoice i " +
//            "where i.client_id=:id and i.is_active =1 and i.insert_time:localdate",
//            nativeQuery = true)
//    List<Invoice> findByClientIdAndDateNow(Long id, String localDate);

    @Query(value ="select * from invoice i where i.invoice_date between :ilk and :son and i.client_id=:id and i.invoice_type=:invoiceType and length(i.invoice_number)>7",nativeQuery = true)
    List<Invoice> findByClientIdAndDateNow(Long id,String ilk,String son, Integer invoiceType);

    @Query(value = "select * from invoice i where i.client_id=:client_id and i.is_active=true" +
            " and (:buyerName is null or i.buyer_name like concat(:buyerName) )" +
            " and (:invoiceDate is null or i.invoice_date >= :invoiceDate)" +
            " and (:invoiceEndDate is null or i.invoice_date <= :invoiceEndDate)" +
            " and (i.invoice_type IN :invoiceTypes)" +
            " and (:search  is null or i.buyer_name like concat('%',:search, '%'))" +
            " and (:currency is null or i.currency_code like concat('%', :currency, '%'))"+
            " order by i.insert_time  desc", nativeQuery = true)
    Page<Invoice> findAllByFilter(String buyerName,String currency, LocalDate invoiceDate, LocalDate invoiceEndDate, Long client_id, List<String> invoiceTypes, String search, Pageable page);
}