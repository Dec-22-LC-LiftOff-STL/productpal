package org.launchcode.productpal.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterFormDTO extends LoginFormDTO {

    private String verifyPassword;

    @NotNull
    @NotBlank
    private String email;

    public String getVerifyPassword() {

        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {

        this.verifyPassword = verifyPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}