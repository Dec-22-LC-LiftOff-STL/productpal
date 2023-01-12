package org.launchcode.productpal.controllers;

import net.bytebuddy.utility.RandomString;
import org.launchcode.productpal.models.User;
import org.launchcode.productpal.models.UserNotFoundException;
import org.launchcode.productpal.models.UserServices;
import org.launchcode.productpal.models.Utility;
import org.launchcode.productpal.models.data.ProductRepository;
import org.launchcode.productpal.models.dto.ResetFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserServices userService;

    @Autowired
    private ProductRepository productRepository;

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
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        User user = userService.getByResetPasswordToken(token);

        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }

}