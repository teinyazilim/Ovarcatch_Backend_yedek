package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.dto.CashInvoiceDTO;
import com.tein.overcatchbackend.domain.model.BankTransaction;
import com.tein.overcatchbackend.domain.model.CashInvoice;
import com.tein.overcatchbackend.domain.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeExpenseRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "select * from Invoice i where i.client_id=:clientId" +
            " and (:startDate is null or i.invoice_date >= :startDate)" +
            " and (:endDate is null or i.invoice_date <= :endDate)" +
            " and (i.invoice_type is null or i.invoice_type in :invoiceType)" +
            "and i.is_active=true", nativeQuery = true)
    List<Invoice> getForReportByFilter(Long clientId, LocalDate startDate, LocalDate endDate, List<Integer> invoiceType);

    @Query(value = "select i from Invoice i where i.client.id=:clientId" +
            " and (:startDate is null or i.invoiceDate >= :startDate)" +
            " and (:endDate is null or i.invoiceDate <= :endDate)" +
            "and i.isActive=true")
    List<Invoice> getForReportByFilter1(Long clientId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select ci from CashInvoice ci where ci.client.id=:clientId" +
            " and (:startDate is null or ci.invoiceDate >= :startDate)" +
            " and (:endDate is null or ci.invoiceDate <= :endDate)" +
            " and (:exType is null or ci.cashInvoiceType = :exType)" +
            " and ci.isActive=true")
    List<CashInvoice> getForReportByFilterCash1(Long clientId, LocalDate startDate, LocalDate endDate, String exType);

    @Query(value = "select ci from CashInvoice ci where ci.client.id=:clientId" +
            " and (:startDate is null or ci.invoiceDate >= :startDate)" +
            " and (:endDate is null or ci.invoiceDate <= :endDate)" +
            " and (ci.cashInvoiceType is null or ci.cashInvoiceType in :typeExs)" +
            " and ci.isActive=true")
    List<CashInvoice> getForReportByFilterCash(Long clientId, LocalDate startDate, LocalDate endDate, List<String> typeExs);

    @Query(value = "select bt from BankTransaction bt where bt.client.id=:clientId" +
            " and (:startDate is null or bt.startDate >= :startDate)" +
            " and (:endDate is null or bt.startDate <= :endDate)" +
            " and bt.isActive=true")
    List<BankTransaction> getForReportFilterBank(Long clientId, LocalDate startDate, LocalDate endDate);
}
