package com.web.time_to_book.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.HomeController;
import com.example.demo.viewmodel.done.BaseViewModel;
import com.example.demo.viewmodel.done.HomeViewModel;

@Controller
@RequestMapping("/")
public class HomeControllerImpl implements HomeController {

    @Override
    @GetMapping
    public String homePage(Model model) {
        var viewModel = new HomeViewModel(createBaseViewModel("Главная"), null);

        model.addAttribute("home", viewModel);
        return "home";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
