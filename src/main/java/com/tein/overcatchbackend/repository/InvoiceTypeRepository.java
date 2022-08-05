package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Address;
import com.tein.overcatchbackend.domain.model.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceTypeRepository extends JpaRepository<InvoiceType, Long> {

    @Query(value ="select i from InvoiceType  i where i.clientId= :clientId and ( :invoiceType is null or i.invoiceType like concat('%',:invoiceType ,'%')) and i.isActive=true")
    InvoiceType findByClientIdInvoiceType (Long clientId, String invoiceType);

    @Query(value ="select i from InvoiceType  i where i.clientId= :clientId and i.isActive = true ")
    List<InvoiceType> findByClientId (Long clientId);
}
