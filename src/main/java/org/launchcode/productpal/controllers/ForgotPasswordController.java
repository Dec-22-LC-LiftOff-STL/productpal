package org.launchcode.productpal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.utility.RandomString;
import org.launchcode.productpal.models.User;
import org.launchcode.productpal.models.UserNotFoundException;
import org.launchcode.productpal.models.UserServices;
import org.launchcode.productpal.models.Utility;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserServices userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepository;

    private static final Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);


    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }


    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        User user = null;
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println(resetPasswordLink);
            sendEmail(email, "Your Password Reset Link", resetPasswordLink);

            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot_password_form";
    }


    public void sendEmail(String to, String subject, String link)
            throws MessagingException, UnsupportedEncodingException {

            String content = "Hello, "
                + "You have requested to reset your Product Pal password."
                + "Click the link to change your password: "
                + link;

        SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("product.pal@zohomail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) throws UserNotFoundException {
        if(token == null) {
            model.addAttribute("message", "Token cannot be null");
            return "message";
        }
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }


    @PostMapping("/reset_password")
    public String processResetPassword(@Param(value="token") String token, @Param(value="password") String password, HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException, UserNotFoundException {
        log.info("Received request to reset password");
//        String token = request.getParameter("token");
//        String password = request.getParameter("password");
        if(password == null || password.isEmpty()){
            model.addAttribute("message", "password can't be null or empty.");
            return "message";
        }
        User user = userService.getByResetPasswordToken(token);
        log.info("Retrieved user from service with token: {}", token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            log.info("Invalid token provided: {}", token);
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            user.setPwHash(password);
            userRepo.save(user);
            log.info("Changed password for user with email: {}", user.getEmail());
            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }



}