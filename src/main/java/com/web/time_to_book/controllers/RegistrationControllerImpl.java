package com.web.time_to_book.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.RegistrationController;
import com.example.demo.forms.done.UserRegistrationForm;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.pages.RegistrationViewModel;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationControllerImpl implements RegistrationController {

    private UserService userService;

    @Autowired
    public void setRegistrationControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public String registrationUserForm(Model model) {
        var registrationViewModel = new UserRegistrationForm("", "", "", "", "", "");
        var viewModel = new RegistrationViewModel(createBaseViewModel("Регистрация"), registrationViewModel);
        model.addAttribute("model", viewModel);
        return "reg";
    }

    @Override
    @PostMapping
    public String registrationUser(@Valid UserRegistrationForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var registrationViewModel = new UserRegistrationForm("", "", "", "", "", "");
            var viewModel = new RegistrationViewModel(createBaseViewModel("Регистрация"), registrationViewModel);
            model.addAttribute("model", viewModel);
            return "reg";
        }

        UserRequestDTO user = new UserRequestDTO(form.firstName(), form.lastName(), form.userName(), form.email(),
                form.password(), form.phoneNumber());
        userService.addUser(user);
        return "redirect:/";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
