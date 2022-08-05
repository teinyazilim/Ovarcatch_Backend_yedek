package com.tein.overcatchbackend.enums;

public enum  FileType {
    USER_PROFILE_IMAGE("Kullanıcı Profil Resmi"),
    BANK_TRANSACTION("Kullanıcı Banka Resmi"),
    ;

    private final String description;

    FileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
