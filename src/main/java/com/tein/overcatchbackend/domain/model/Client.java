package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.AgreementType;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CLIENT"
        //  ,indexes = {        @Index(name = "COMPANY_NAME_X", columnList = "COMPANY_NAME", unique = true)}
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Client extends BaseEntity {

    @Size(max = 210100)
    @Column(name = "NOTES")
    private String notes;

    @Column(name = "CLIENTFOLDER")
    private String clientFolder;

    @Column(name = "CLIENTFILENAME")
    private String clientFileName;

    @Column(name = "IS_EXISTING")
    private Boolean isExisting;

    @Column(name = "IS_VAT_MEMBER")
    private Boolean isVatMember;

    @Column(name="STATE",length = 1)
    private String state;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "YEAR_END_DATE")
    private LocalDate yearEndDate;

    @Column(name = "VAT_NUMBER")
    private String vatNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "VAT_REGISTRATION_DATE")
    private LocalDate vatRegistrationDate;

    @Column(name = "VAT_PERIOD_END")
    private LocalDate vatPeriodEnd;

    @Column(name = "GATEWAY_ID")
    private String gateway;

    @Enumerated(EnumType.STRING)
    @Column(name = "CLIENT_TYPE")
    private ClientTypeEnum clientTypeEnum;

    @Column(name = "CODE")
    private String code;

    @Column(name = "VISA_TYPE")
    private String visaType;

    @Column(name = "IS_INVOICE_NUMBER")
    private Boolean isInvoiceNumber=true;

    @Column(name = "INVOICE_NUMBER")
    private Integer invoiceNumber=1;

    @Column(name = "VAT_FLAT_RATE")
    private String vatFlatRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "AGGREMENT_TYPE")
    private AgreementType agreementType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "client",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerClient> customerClients;

    @JsonIgnore
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private List<Address> addressList;

    @JsonIgnore
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private List<AddressNew> addressNewList;

    @JsonIgnore
    @OneToOne(mappedBy = "clientInfo",cascade=CascadeType.ALL)
    private FounderOwner founderOwner;

    @JsonIgnore
    @OneToOne(mappedBy = "clientInfo",cascade=CascadeType.ALL, orphanRemoval = true)
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tasks> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<BankTransaction> bankTransactions;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Letter> letters;

    @Column(name = "PAYMENT")
    private String payment;

    @Column(name = "SELECTEDINVOICE" )
    private Integer selectedInvoiceType;

    @ManyToOne
    @JoinColumn(name = "INVOICE_TYPE_ID", referencedColumnName = "id")
    private InvoiceType invoice_typeId;

    @Column(name = "WEB")
    private String web;

    @Column(name = "ISEMAILSEND")
    private Boolean isMailSend;

    @ManyToOne
    @JoinColumn(name = "BANK_ID",referencedColumnName = "id")
    @JsonIgnore
    private Bank bank;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "STATUS_COMPLETED")
    private Boolean status_completed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "REMINDERDATE")
    private LocalDate reminderDate;

    @Column(name = "ReminderRepeat")
    private Integer reminderRepeat;

    public void setCompany(Company company) {
        if (company != null) {
            this.company = company;
            this.company.setClientInfo(this);
        }
    }
    public void setFounderOwner(FounderOwner founderOwner) {
        if (founderOwner != null) {
            this.founderOwner = founderOwner;
            this.founderOwner.setClientInfo(this);
        }
    }

    public void setBankTransactions(List<BankTransaction> bankTransactions) {
        if (bankTransactions != null) {
            this.bankTransactions = new ArrayList<>();
            bankTransactions.forEach(item -> {
                this.bankTransactions.add(item);
                item.setClient(this);
            });
        }
    }
    public void setAddressList(List<Address> addresses) {
        if (addresses != null) {
            this.addressList = new ArrayList<>();
            addresses.forEach(item -> {
                this.addressList.add(item);
                item.setClient(this);
            });
        }
    }
    //Aşağıdaki Kod yeni eklendi...
    public void setNewAddressList(List<AddressNew> addressNewList , Client client) {
        if (addressNewList != null) {
            this.addressNewList = new ArrayList<>();
            addressNewList.forEach( item ->{
                item.setClient_address(client.getId());
                item.setIsActive(true);
                item.setAddressType(item.getAddressType());
                item.setCity(item.getCity());
                item.setCountry(item.getCountry());
                item.setCounty(item.getCounty());
                item.setDistrict(item.getDistrict());
                item.setNumber(item.getNumber());
                item.setPostcode(item.getPostcode());
                item.setStreet(item.getStreet());
                this.addressNewList.add(item);
            });
        }
    }
    // Yukarıdaki kod yeni eklendi
    public void setDocuments(List<Document> documents) {
        if (documents != null) {
            this.documents = new ArrayList<>();
            documents.forEach(item -> {
                this.documents.add(item);
                item.setClient(this);
            });
        }
    }
}
