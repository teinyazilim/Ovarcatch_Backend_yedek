package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.DocumentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "DOCUMENT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Document extends BaseEntity {

    @NotNull
    @Column(name = "process_id")
    private String processId;

    @Column(name = "USER_ID")
    private Long userID;

    @Enumerated(EnumType.STRING)
    @Column(name= "DOCUMENT_TYPE")
    private DocumentType documentType;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "FILE_DESCRIPTION")
    private String fileDescription;

    @JsonIgnore
    @OneToOne(mappedBy = "document")
    private BankTransaction bankTransaction;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @JsonIgnore
    @OneToOne(mappedBy = "document")
    private Letter letter;

}
