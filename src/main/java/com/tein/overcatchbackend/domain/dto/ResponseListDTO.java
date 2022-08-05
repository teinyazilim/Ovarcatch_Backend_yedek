package com.tein.overcatchbackend.domain.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseListDTO<T> implements Serializable {
    int pages;
    int currentPage;
    int status;
    String message;

    private List<T> data;


    public ResponseListDTO(int pages, int currentPage, int status, String message, List<T> data) {
        this.pages = pages;
        this.currentPage = currentPage;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
