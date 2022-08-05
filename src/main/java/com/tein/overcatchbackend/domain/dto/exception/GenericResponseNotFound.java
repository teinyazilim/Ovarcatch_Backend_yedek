package com.tein.overcatchbackend.domain.dto.exception;

public class GenericResponseNotFound {
    private boolean success = false;

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public GenericResponseNotFound() {
    }

    public GenericResponseNotFound(String message) {
        this.success = false;
        this.message = message;
    }


}
