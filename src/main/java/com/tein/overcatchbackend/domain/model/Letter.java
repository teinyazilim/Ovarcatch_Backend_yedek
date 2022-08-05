package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.PageStatus;
import com.tein.overcatchbackend.enums.SessionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "LETTERS")
@EqualsAndHashCode(callSuper = true)
public class Letter extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "TASK_ID")
    private Tasks task;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "LETTER_TYPE_ID")
    private LetterType letterType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @Lob
    @Column(name = "LETTER", length = 100000)
    private String letter;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "REQUEST_DATE")
    private LocalDateTime requestDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "RECOGNITION_DATE")
    private LocalDateTime recognitionDate;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "FILE_SIZE")
    private Float fileSize;

    @Column(name = "USER_ROLE")
    private String userRole;


}
