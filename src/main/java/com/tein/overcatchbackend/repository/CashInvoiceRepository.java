package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.CashInvoice;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.domain.model.Invoice;
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
public interface CashInvoiceRepository extends JpaRepository<CashInvoice, Long> {
    @Query(value ="select i from CashInvoice i where i.client.id=:id and i.isActive=true")
    Page<CashInvoice> findAllByClientId(Long id, Pageable page);

    @Query(value ="select i from CashInvoice i where i.client.id=:id and i.isActive=true")
    List<CashInvoice> findAllByClientId1(Long id);

    @Modifying
    @Transactional
    @Query(value ="update CashInvoice i set i.isActive = 0 where i.id = :id ")
    void deleteCashInvoiceById(Long id);

    @Query(value = "select * from cash_invoice ci where ci.client_id=:client_id and ci.is_active=true" +
            " and (:invoiceCurrency is null or ci.currency_code like concat('%',:invoiceCurrency,'%') )" +
            " and (:cashInvoiceType is null or ci.cash_invoice_type_id = :cashInvoiceType)" +
            " and (:invoiceDate is null or ci.cash_invoice_date >= :invoiceDate)" +
            " and (:invoiceEndDate is null or ci.cash_invoice_date <= :invoiceEndDate)"+
            " and (:search is null or ci.cash_invoice_type_id like concat('%',:search,'%'))", nativeQuery = true)
    Page<CashInvoice> findAllByFilter(String invoiceCurrency, LocalDate invoiceDate, LocalDate invoiceEndDate, Long client_id,String cashInvoiceType,String search, Pageable page);

    @Query(value = "select * from cash_invoice ci where ci.client_id=:client_id and ci.is_active=true " +
            " and (:invoiceCurrency is null or ci.currency_code like concat('%',:invoiceCurrency,'%') )" +
            " and (:cashInvoiceType is null or ci.cash_invoice_type_id = :cashInvoiceType)" +
            " and (:invoiceDate is null or ci.cash_invoice_date >= :invoiceDate)" +
            " and (:invoiceEndDate is null or ci.cash_invoice_date <= :invoiceEndDate)" +
            " and (:search is null or ci.cash_invoice_type_id like concat('%',:search,'%'))", nativeQuery = true)
    List<CashInvoice> findByFilterForExcell(String invoiceCurrency, LocalDate invoiceDate, LocalDate invoiceEndDate, Long client_id,String cashInvoiceType,String search);

    CashInvoice findByTaskId (Long taskId);
}



