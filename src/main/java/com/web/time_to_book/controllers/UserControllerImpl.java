package com.web.time_to_book.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.controllers.done.UserController;
import com.example.demo.forms.done.UserProfileUpdateForm;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.user.UserProfileUpdateViewModel;
import com.example.demo.viewmodel.user.UserProfileViewModel;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
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
                userResponseDTO.getPhoneNumber(),
                userResponseDTO.getNumberOfAppointments());

        model.addAttribute("model", viewModel);
        return "profile";
    }

    @Override
    @GetMapping("/{id}/update")
    public String updateUserForm(@PathVariable("id") UUID id, Model model) {
        var updateViewModel = new UserProfileUpdateForm("", "", "", "", "", "*****");
        var viewModel = new UserProfileUpdateViewModel(createBaseViewModel("Редактирование профиля"), updateViewModel);
        model.addAttribute("model", viewModel);
        return "profile-update";
    }

    @Override
    @PostMapping("/{id}/update")
    public String updateUser(@Valid @PathVariable("id") UUID id, UserProfileUpdateForm form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var updateViewModel = new UserProfileUpdateForm("", "", "", "", "", "*****");
            var viewModel = new UserProfileUpdateViewModel(createBaseViewModel("Редактирование профиля"),
                    updateViewModel);
            model.addAttribute("model", viewModel);
            return "profile-update";
        }

        userService.updateUser(id, new UserRequestDTO(form.firstName(), form.lastName(), form.userName(), form.email(),
                form.password(), form.phoneNumber()));
        return "redirect:/user/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
