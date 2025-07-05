package com.bintang.customerservice.dto;

public class SearchEmailRequest {
    private String email;

    public SearchEmailRequest(String email) {
        this.email = email;
    }

    public SearchEmailRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
