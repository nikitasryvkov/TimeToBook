package com.web.time_to_book.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.HomeController;
import com.example.demo.viewmodel.category.CategoryViewModel;
import com.example.demo.viewmodel.done.BaseViewModel;
import com.example.demo.viewmodel.done.HomeViewModel;
import com.web.time_to_book.services.CategoryService;

@Controller
@RequestMapping("/")
public class HomeControllerImpl implements HomeController {

    private CategoryService categoryService;

    @Autowired
    public void setHomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @GetMapping
    public String homePage(Model model) {
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> new CategoryViewModel(category.getName())).collect(Collectors.toList());
        var viewModel = new HomeViewModel(createBaseViewModel("Главная"), categoryViewModel);
        model.addAttribute("model", viewModel);
        return "home";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
