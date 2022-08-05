package com.tein.overcatchbackend.domain.vm;

import com.tein.overcatchbackend.domain.dto.LoginDTO;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    private LoginDTO user;

    public LoginDTO getUser() {
        return user;
    }

    public void setUser(LoginDTO user) {
        this.user = user;
    }


    public JwtAuthenticationResponse(String accessToken, LoginDTO user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


}
