package com.web.time_to_book.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.UserController;
import com.example.demo.forms.done.UserUpdateForm;
import com.example.demo.viewmodel.done.BaseViewModel;
import com.example.demo.viewmodel.done.UserProfileViewModel;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Autowired
    public void setUserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") UUID id, Model model) {
        var userResponseDTO = userService.findById(id);
        var viewModel = new UserProfileViewModel(
                createBaseViewModel("Профиль"),
                userResponseDTO.getFirstName(),
                userResponseDTO.getLastName(),
                userResponseDTO.getUsername(),
                userResponseDTO.getEmail(),
                userResponseDTO.getEmail(),
                userResponseDTO.getNumberOfAppointments());

        model.addAttribute("user", viewModel);
        return "profile";
    }

    @Override
    public String updateUserForm(UUID id, Model model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserForm'");
    }

    @Override
    public String updateUser(@Valid UUID id, UserUpdateForm form, BindingResult bindingResult, Model model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
