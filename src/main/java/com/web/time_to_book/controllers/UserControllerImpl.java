package com.web.time_to_book.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.controllers.done.UserController;
import com.example.demo.forms.done.UserProfileUpdateForm;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.user.UserProfileUpdateViewModel;
import com.example.demo.viewmodel.user.UserProfileViewModel;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;
import com.web.time_to_book.models.User;
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
    public String getUser(Principal principal, Model model) {
        if (principal == null) {
            throw new RuntimeException("Principal is null");
        }
        String userName = principal.getName();
        User user = userService.getUser(userName);
        Integer countAppointment = userService.countAppointment(user.getId());
        var viewModel = new UserProfileViewModel(
                createBaseViewModel("Профиль"),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                countAppointment);

        model.addAttribute("model", viewModel);
        return "profile";
    }

    @Override
    @GetMapping("/profile/update")
    public String updateUserForm(Principal principal, Model model) {
        if (principal == null) {
            throw new RuntimeException("Principal is null");
        }

        var user = userService.findByUsername(principal.getName());
        var updateViewModel = new UserProfileUpdateForm(user.getFirstName(), user.getLastName(), user.getPhoneNumber());
        var viewModel = new UserProfileUpdateViewModel(createBaseViewModel("Редактирование профиля"));
        model.addAttribute("form", updateViewModel);
        model.addAttribute("model", viewModel);
        return "profile-update";
    }

    @Override
    @PatchMapping("/profile/update")
    public String updateUser(Principal principal, @Valid @ModelAttribute("form") UserProfileUpdateForm form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var updateViewModel = form;
            var viewModel = new UserProfileUpdateViewModel(createBaseViewModel("Редактирование профиля"));
            model.addAttribute("form", updateViewModel);
            model.addAttribute("model", viewModel);
            return "profile-update";
        }

        var user = userService.findByUsername(principal.getName());
        UserRequestDTO userDTO = new UserRequestDTO(form.firstName(), form.lastName(), form.phoneNumber());

        userService.updateUser(user.getId(), userDTO);
        return "redirect:/profile";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
