package com.utilitybilling.authservice.dto;

public class LoginResponse{

    private String accessToken;
    private String tokenType;
    private String role;

    public LoginResponse(String accessToken,String role){
        this.accessToken=accessToken;
        this.tokenType="Bearer";
        this.role=role;
    }

    public String getAccessToken(){return accessToken;}
    public String getTokenType(){return tokenType;}
    public String getRole(){return role;}
}
