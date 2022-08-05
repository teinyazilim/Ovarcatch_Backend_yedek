package com.tein.overcatchbackend.enums;

import java.util.HashMap;
import java.util.Map;

public enum ClientTypeEnum {
    SOLETRADE(001, "SOLE TRADE"),
    LIMITED(002, "LIMITED"),
    SELFASSESMENT(004, "SELF ASSESMENT");
    private final Integer key;
    private final String value;

    ClientTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    static Map<Integer, ClientTypeEnum> map = new HashMap<>();

    static {
        for (ClientTypeEnum st : ClientTypeEnum.values()) {
            map.put(st.key, st);
        }
    }
    public static ClientTypeEnum getByCode(int code) {
        return map.get(code);
    }
}
