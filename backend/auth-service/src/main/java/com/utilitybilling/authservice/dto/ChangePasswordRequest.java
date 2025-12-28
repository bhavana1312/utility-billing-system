package com.utilitybilling.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ChangePasswordRequest{

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Pattern(
        regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
        message="Password must be strong"
    )
    private String newPassword;

    public String getOldPassword(){return oldPassword;}
    public void setOldPassword(String oldPassword){this.oldPassword=oldPassword;}

    public String getNewPassword(){return newPassword;}
    public void setNewPassword(String newPassword){this.newPassword=newPassword;}
}
