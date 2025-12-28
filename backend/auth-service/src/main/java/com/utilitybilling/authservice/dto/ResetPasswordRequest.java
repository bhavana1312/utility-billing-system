package com.utilitybilling.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ResetPasswordRequest{

    @NotBlank
    private String resetToken;

    @NotBlank
    @Pattern(
        regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
        message="Password must be strong"
    )
    private String newPassword;

    public String getResetToken(){return resetToken;}
    public void setResetToken(String resetToken){this.resetToken=resetToken;}

    public String getNewPassword(){return newPassword;}
    public void setNewPassword(String newPassword){this.newPassword=newPassword;}
}
