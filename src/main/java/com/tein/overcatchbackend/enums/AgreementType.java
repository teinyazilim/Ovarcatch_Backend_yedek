package com.tein.overcatchbackend.enums;

import java.util.HashMap;
import java.util.Map;

public enum AgreementType {
    TRADING(001, "TRADING"),
    ECAA(002, "ECAA"),
    OTHER(004, "OTHER");

    private final Integer key;
    private final String value;

    AgreementType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    static Map<Integer, AgreementType> map = new HashMap<>();

    static {
        for (AgreementType st : AgreementType.values()) {
            map.put(st.key, st);
        }
    }
    public static AgreementType getByCode(int code) {
        return map.get(code);
    }
}
