package org.launchcode.productpal.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class User extends AbstractEntity {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Username fields
    @NotNull
    private String username;

    @NotNull
    private String email;

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    // Encrypted password fields
    @NotNull
    private String pwHash;

    // Reset password token
    @Column (name = "one_time_password")
    private String resetPasswordToken;

    public String getOtpRequestedTime() {
        return otpRequestedTime;
    }

    public void setOtpRequestedTime(String otpRequestedTime) {
        this.otpRequestedTime = otpRequestedTime;
    }

    @Column (name = "otp_requested_time")
    private String otpRequestedTime;

    public User() {}

    // Constructor has arguments to encode the password field
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Checks password values to see if they match up
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}