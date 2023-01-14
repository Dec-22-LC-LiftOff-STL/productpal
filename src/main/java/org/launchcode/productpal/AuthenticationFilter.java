package org.launchcode.productpal;

import org.launchcode.productpal.controllers.AuthenticationController;
import org.launchcode.productpal.models.User;
import org.launchcode.productpal.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    // Whitelist variable containing the paths that can be accessed without a user session
    private static final List<String> whitelist = Arrays.asList("/login", "/register", "/logout", "/forgot_password","/forgot_password_form","/reset_password", "/reset_password_form", "/reset_password?token", "/message", "/css");

    // Method that checks a given path against the values in the whitelist
    private static boolean isWhiteListed(String path) {
        for (String pathRoot : whitelist) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    // PreHandle method
    // This must override the inherited method of the same name.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // Grab the session information from the request object.
        // Before looking for session and user status, add a conditional that checks the whitelist status of the current request object
        if (isWhiteListed(request.getRequestURI())) {
            return true;
        }
        //Query the  session data for a user.
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        //If a user exists, return true. Otherwise, redirect to the login page and return false.
        if (user != null) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }


}