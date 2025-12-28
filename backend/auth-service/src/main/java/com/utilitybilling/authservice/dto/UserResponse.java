package com.utilitybilling.authservice.dto;

public class UserResponse{

    private String userId;
    private String username;
    private String email;
    private String role;
    private boolean active;

    public UserResponse(String userId,String username,String email,String role,boolean active){
        this.userId=userId;
        this.username=username;
        this.email=email;
        this.role=role;
        this.active=active;
    }

    public String getUserId(){return userId;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getRole(){return role;}
    public boolean isActive(){return active;}
}
