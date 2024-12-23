package com.web.time_to_book.controllers;

import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.CategoryController;
import com.example.demo.forms.done.CategoryCreateForm;
import com.example.demo.forms.done.CategoryUpdateForm;
import com.example.demo.viewmodel.category.CategoriesViewModel;
import com.example.demo.viewmodel.category.CategoryCreateViewModel;
import com.example.demo.viewmodel.category.CategoryUpdateViewModel;
import com.example.demo.viewmodel.category.CategoryViewModel;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.web.time_to_book.dtos.CategoryDTO;
import com.web.time_to_book.services.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryControllerImpl implements CategoryController {

    private CategoryService categoryService;
    private ModelMapper modelMapper;

    public CategoryControllerImpl(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/all")
    public String getCategory(Model model) {
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
        var viewModel = new CategoriesViewModel(createBaseViewModel("Категории"));

        model.addAttribute("category", categoryViewModel);
        model.addAttribute("model", viewModel);
        return "categories";
    }

    @Override
    @GetMapping("/create")
    public String addCategoryForm(Model model) {
        var viewModel = new CategoryCreateViewModel(createBaseViewModel("Добавить категорию"));
        var categoryForm = new CategoryCreateForm(null);

        model.addAttribute("form", categoryForm);
        model.addAttribute("model", viewModel);
        return "category-create";
    }

    @Override
    @PostMapping("/create")
    public String addCategory(@Valid @ModelAttribute("form") CategoryCreateForm form, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var viewModel = new CategoryCreateViewModel(createBaseViewModel("Добавить категорию"));
            var categoryForm = form;

            model.addAttribute("form", categoryForm);
            model.addAttribute("model", viewModel);
            return "category-create";
        }

        var category = new CategoryDTO(form.name());

        categoryService.addCategory(category);
        return "redirect:/category/all";
    }

    @Override
    @GetMapping("/update/{id}")
    public String updateCategoryForm(@PathVariable("id") UUID id, Model model) {
        var viewModel = new CategoryUpdateViewModel(createBaseViewModel("Изменение категории"));
        var category = categoryService.findById(id);
        var categoryForm = new CategoryUpdateForm(id, category.getName());

        model.addAttribute("form", categoryForm);
        model.addAttribute("model", viewModel);
        return "category-update";
    }

    @Override
    @PatchMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") UUID id, @Valid @ModelAttribute("form") CategoryUpdateForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var viewModel = new CategoryUpdateViewModel(createBaseViewModel("Изменение категории"));
            var categoryForm = form;

            model.addAttribute("form", categoryForm);
            model.addAttribute("model", viewModel);
            return "category-update";
        }

        var category = categoryService.findById(id);
        category.setName(form.name());
        categoryService.updateCategory(id, category);

        return "redirect:/category/all";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
