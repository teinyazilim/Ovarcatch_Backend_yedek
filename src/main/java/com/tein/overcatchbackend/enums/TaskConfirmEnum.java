package com.tein.overcatchbackend.enums;

public enum TaskConfirmEnum {
    INPROGRESS,
    DONE,
    REJECTED;

    public String getDescriptionTR(String value) {
        if (value.equals("DONE")){
            return  "onaylandı.";
        }
        else if (value.equals("REJECTED")){
            return "reddedildi.";
        }
        else {
            return "beklemede.";
        }
    }
}
