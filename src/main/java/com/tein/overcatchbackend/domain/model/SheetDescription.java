package com.tein.overcatchbackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SheetDescription {
    private Integer sheetNumber;
    private Integer sayfaSonu;
    private Integer sayfaBasi;
    private Boolean isBosluk=false;
}