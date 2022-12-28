package org.launchcode.productpal.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class User  extends AbstractEntity {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Username fields
    @NotNull
    private String username;

    // Encrypted password fields
    @NotNull
    private String pwHash;

    public User() {}

    // Constructor has arguments to encode the password field
    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    // Checks password values to see if they match up
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}