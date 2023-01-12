package org.launchcode.productpal.models;

import org.launchcode.productpal.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class UserServices {

    @Autowired
    private UserRepository userRepo;

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        User user = userRepo.findByResetPasswordToken(token);
        userRepo.save(user);
        return user;
        }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPwHash(encodedPassword);
        user.setResetPasswordToken(null);
        userRepo.save(user);
    }

}