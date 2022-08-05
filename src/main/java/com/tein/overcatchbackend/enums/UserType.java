package com.tein.overcatchbackend.enums;

public enum UserType {

    CUSTOMER("Müşteri Tipi"),
    EMPLOYEE("Personel Tipi"),
    MANAGER("Yönetici Tipi");
    private final String description;

    UserType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
