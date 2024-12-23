package com.web.time_to_book.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.controllers.done.FaqController;
import com.example.demo.viewmodel.pages.BaseViewModel;

@Controller
public class FaqControllerImpl implements FaqController {

    @GetMapping("/faqs")
    public String getFaqPage() {
        return "faqs";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
