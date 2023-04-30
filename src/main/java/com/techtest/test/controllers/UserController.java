package com.techtest.test.controllers;

import com.techtest.test.dtos.UserDto;
import com.techtest.test.entities.User;
import com.techtest.test.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/register")
    public String login(Model model) {

            model.addAttribute("user", new UserDto());
            return "register";

    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        User u;
        try {
            u = userService.createUser(convertUserDtoToUser(userDto));
        }
        catch (Exception r) {
            model.addAttribute("error", r.getMessage());
            return "error";
        }
        //request.getSession().setAttribute("user", convertUserToDto(u));

        return "redirect:/login";
    }

    private UserDto convertUserToDto(User u) {
        return new UserDto(u);
    }

    private User convertUserDtoToUser(UserDto u) {
        User user = new User();
        user.setUsername(u.getUsername());
        user.setName(u.getName());
        user.setPassword(u.getPassword());
        user.setAddress(u.getAddress());
        return user;
    }
}
