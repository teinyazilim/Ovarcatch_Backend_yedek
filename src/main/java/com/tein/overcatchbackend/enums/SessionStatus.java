package com.tein.overcatchbackend.enums;

import java.util.HashMap;
import java.util.Map;

public enum SessionStatus {

    START(001, "Başladı"),
    INPROGESS(002, "Devam Ediyor"),
    DONE(003,"Tamamlandı"),
    REJECT(004,"Ret Edildi");

    private final Integer key;
    private final String value;

    SessionStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    static Map<Integer, SessionStatus> map = new HashMap<>();

    static {
        for (SessionStatus st : SessionStatus.values()) {
            map.put(st.key, st);
        }
    }

    public static SessionStatus getByCode(int code) {
        return map.get(code);
    }
}
