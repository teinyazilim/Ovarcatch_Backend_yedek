package com.tein.overcatchbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(
            name = "base_seq_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.OPT_PARAM, value = "pooled-lo"),
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1000"),
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"),
                    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "BASE_SEQ"),
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_seq_generator")
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false, precision = 15, scale = 0, columnDefinition = "NUMERIC")
    protected Long id;

   @Column(name = "is_active")
    private Boolean isActive;

    @UpdateTimestamp
    @Column(name = "UPDATE_TIME", columnDefinition = "TIMESTAMP")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdatedDateTime;

    @CreationTimestamp
    @Column(name = "INSERT_TIME", columnDefinition = "TIMESTAMP")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDateTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseEntity baseEntity = (BaseEntity) o;
        if (baseEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString()
    {
        return "BaseEntity{" + "id=" + getId() + "}";
    }
}
