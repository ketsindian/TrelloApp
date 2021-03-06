package com.trello.security;


public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenerateJwts";
    public static final long VALIDITY = 5 * 60 * 60; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHENTICATE_PERMIL_URL = "/auth/**";
}

