package com.tein.overcatchbackend.enums;

import java.util.HashMap;
import java.util.Map;

public enum PageStatus {

    CUSTOMER(001, "CUSTOMER PAGE"),
    EMPLOYEE(002, "EMPLOYEE PAGE"),
    MANAGER(003,"MANAGER PAGE");

    private final Integer key;
    private final String value;

    PageStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    static Map<Integer, PageStatus> map = new HashMap<>();

    static {
        for (PageStatus st : PageStatus.values()) {
            map.put(st.key, st);
        }
    }

    public static PageStatus getByCode(int code) {
        return map.get(code);
    }

}
