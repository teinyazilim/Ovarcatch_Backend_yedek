package com.tein.overcatchbackend.security.jwt;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 880_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHENTICATE_URL = "/api/auth/authenticate";
    public static final String AUTHENTICATE_SIGN_IN = "/api/auth/signin";
    public static final String AUTHENTICATE_SIGN_UP = "/api/auth/signup";
    public static final String CREATE_URL = "/api/users/save";
    public static final String CREATE_USER_URL = "/api/auth/register";
    public static final String UPDATE_USER_URL = "/api/auth/updateUser";
    public static final String ACTIVATE_USER_URL = "/api/auth/activateUser/{username}";
    public static final String DEACTIVATE_USER_URL = "/api/auth/deactivateUser/{username}";
}
