package com.tein.overcatchbackend.domain.vm;

public class AuthToken {

    private String token;
    private String roleCode;
    private String email;


    public AuthToken(String token, String roleCode, String email) {
        this.token = token;
        this.roleCode = roleCode;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
