package com.web.time_to_book.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.SigInController;
import com.example.demo.forms.done.UserLoginForm;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.pages.LoginViewModel;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements SigInController {

    private UserService userService;

    @Autowired
    public void setSignInControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public String loginUserForm(Model model) {
        var loginViewModel = new UserLoginForm("", "*****");
        var viewModel = new LoginViewModel(createBaseViewModel("Вход"), loginViewModel);
        model.addAttribute("model", viewModel);
        return "login";
    }

    @Override
    @PostMapping
    public String loginUser(@Valid UserLoginForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var loginViewModel = new UserLoginForm("", "*****");
            var viewModel = new LoginViewModel(createBaseViewModel("Вход"), loginViewModel);
            model.addAttribute("model", viewModel);
            return "login";
        }

        UUID user = userService.verify(form.userName());

        return "redirect:/%s".formatted(user);
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
