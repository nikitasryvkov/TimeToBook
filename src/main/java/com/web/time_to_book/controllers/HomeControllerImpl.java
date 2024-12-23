package com.web.time_to_book.controllers;

import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.HomeController;
import com.example.demo.viewmodel.category.CategoryViewModel;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.pages.HomeViewModel;
import com.web.time_to_book.services.CategoryService;

@Controller
@RequestMapping("/")
public class HomeControllerImpl implements HomeController {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Controller.class);
    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public void setHomeController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping
    public String homePage(Model model) {
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
        var viewModel = new HomeViewModel(createBaseViewModel("Главная"), categoryViewModel);

        LOG.log(Level.INFO, "Open home page");

        model.addAttribute("model", viewModel);
        return "home";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }

}
