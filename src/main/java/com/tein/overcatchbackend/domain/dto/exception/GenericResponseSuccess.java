package com.tein.overcatchbackend.domain.dto.exception;

public class GenericResponseSuccess {

    private boolean success = false;

    private Object user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public Object getObject() {
        return user;
    }

    public void setObject(Object object) {
        this.user = object;
    }

    public GenericResponseSuccess() {
    }

    public GenericResponseSuccess(Object user) {
        this.success = true;
        this.user = user;
    }
}

