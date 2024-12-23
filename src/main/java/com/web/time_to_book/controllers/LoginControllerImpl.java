package com.web.time_to_book.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.controllers.done.LoginController;
import com.example.demo.forms.done.UserLoginForm;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.pages.LoginViewModel;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {

    private UserService userService;

    @Autowired
    public void setSignInControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public String loginUserForm(Model model) {
        return "login";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

    @PostMapping("/error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY,
                username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/login";
    }
}
