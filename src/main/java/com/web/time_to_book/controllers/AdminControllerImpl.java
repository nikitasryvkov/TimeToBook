package com.web.time_to_book.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.AdminController;
import com.example.demo.viewmodel.pages.BaseViewModel;

@Controller
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {

    @Override
    public String adminPage(Model model) {
        // #TODO Реализовать страницу админки с выводом всей возможной информации на
        // одну страницу
        return "admin";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
