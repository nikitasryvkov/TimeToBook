package com.web.time_to_book.controllers;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.ServiceProductController;
import com.example.demo.forms.done.CategoryUpdateForm;
import com.example.demo.forms.done.ServiceProductCreateForm;
import com.example.demo.forms.done.ServiceProductUpdateForm;
import com.example.demo.viewmodel.category.CategoryViewModel;
import com.example.demo.viewmodel.feedback.FeedbackViewModel;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceCreateViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductsViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceUpdateViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceViewModel;
import com.example.demo.viewmodel.user.UserMasterViewModel;
import com.web.time_to_book.dtos.request.ServiceProductRequestDTO;
import com.web.time_to_book.services.CategoryService;
import com.web.time_to_book.services.FeedbackService;
import com.web.time_to_book.services.ServiceProductService;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/service")
public class ServiceProductControllerImpl implements ServiceProductController {

    private ServiceProductService serviceProductService;
    private CategoryService categoryService;
    private FeedbackService feedbackService;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public void setServiceProductControllerImpl(ServiceProductService serviceProductService,
            CategoryService categoryService, FeedbackService feedbackService, UserService userService,
            ModelMapper modelMapper) {
        this.serviceProductService = serviceProductService;
        this.categoryService = categoryService;
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/all")
    public String getServiceProducts(Model model) {
        var serviceViewModel = serviceProductService.findAllServices().stream()
                .map(service -> modelMapper.map(service, ServiceProductsViewModel.class)).collect(Collectors.toList());
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
        var viewModel = new ServiceViewModel(createBaseViewModel("Услуги"));

        model.addAttribute("service", serviceViewModel);
        model.addAttribute("category", categoryViewModel);
        model.addAttribute("model", viewModel);
        return "services";
    }

    @Override
    @GetMapping("/all/master")
    public String getServiceProductsMaster(Principal principal, Model model) {
        var master = userService.findByUsername(principal.getName());
        var serviceViewModel = serviceProductService.findAllServicesMasters(master.getId()).stream()
                .map(service -> modelMapper.map(service, ServiceProductsViewModel.class)).collect(Collectors.toList());
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
        var viewModel = new ServiceViewModel(createBaseViewModel("Услуги"));

        model.addAttribute("service", serviceViewModel);
        model.addAttribute("category", categoryViewModel);
        model.addAttribute("model", viewModel);
        return "services-admin-master";
    }

    @Override
    @GetMapping("/all/admin")
    public String getServiceProductsAdmin(Model model) {
        var serviceViewModel = serviceProductService.findAllServices().stream()
                .map(service -> modelMapper.map(service, ServiceProductsViewModel.class)).collect(Collectors.toList());
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
        var viewModel = new ServiceViewModel(createBaseViewModel("Услуги"));

        model.addAttribute("service", serviceViewModel);
        model.addAttribute("category", categoryViewModel);
        model.addAttribute("model", viewModel);
        return "services-admin-master";
    }

    @GetMapping("/all-service")
    public String getServiceProductsAll(Model model) {
        var serviceViewModel = serviceProductService.findAllServices().stream()
                .map(service -> modelMapper.map(service, ServiceProductsViewModel.class)).collect(Collectors.toList());
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
        var viewModel = new ServiceViewModel(createBaseViewModel("Услуги"));

        model.addAttribute("service", serviceViewModel);
        model.addAttribute("category", categoryViewModel);
        model.addAttribute("model", viewModel);
        return "fragments/service-list :: serviceList";
    }

    @Override
    @GetMapping("/id/{id}")
    public String getServiceProduct(@PathVariable("id") UUID id, Model model) {
        var serviceProductResponseDTO = serviceProductService.findById(id);
        var feedbackViewModel = feedbackService.findAllFeedbackByServiceId(id).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackViewModel.class)).collect(Collectors.toList());
        var viewModel = modelMapper.map(serviceProductResponseDTO, ServiceProductViewModel.class);
        model.addAttribute("feedback", feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "service";
    }

    @GetMapping("/{id}")
    public String getServicesByCategory(@PathVariable UUID id, Model model) {
        List<ServiceProductsViewModel> serviceViewModel;

        if (id == null) {
            serviceViewModel = serviceProductService.findAllServices().stream()
                    .map(service -> modelMapper.map(service, ServiceProductsViewModel.class))
                    .collect(Collectors.toList());
        } else {
            serviceViewModel = serviceProductService.getServicesByCategory(id).stream()
                    .map(service -> modelMapper.map(service, ServiceProductsViewModel.class))
                    .collect(Collectors.toList());
        }

        model.addAttribute("service", serviceViewModel);
        return "fragments/service-list :: serviceList";
    }

    @Override
    @GetMapping("/create/master")
    public String addServiceProductForm(Principal principal, Model model) {
        var viewModel = new ServiceCreateViewModel(createBaseViewModel("Услуга"));
        var master = userService.findByUsername(principal.getName());
        var serviceViewModel = new ServiceProductCreateForm(null, null, null, null, null, master.getId());
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());

        model.addAttribute("category", categoryViewModel);
        model.addAttribute("form", serviceViewModel);
        model.addAttribute("model", viewModel);

        return "service-create";
    }

    @Override
    @PostMapping("/create/master")
    public String addServiceProduct(Principal principal, @Valid @ModelAttribute("form") ServiceProductCreateForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var viewModel = new ServiceCreateViewModel(createBaseViewModel("Услуга"));
            var serviceViewModel = form;
            var categoryViewModel = categoryService.findAllCategories().stream()
                    .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());

            model.addAttribute("category", categoryViewModel);
            model.addAttribute("form", serviceViewModel);
            model.addAttribute("model", viewModel);

            return "service-create";
        }

        var master = userService.findByUsername(principal.getName());
        var serviceProduct = new ServiceProductRequestDTO(form.title(), form.description(), form.categoryId(),
                form.price(), master.getId());
        serviceProductService.addServiceProduct(serviceProduct);

        return "redirect:/service/all/master";
    }

    @Override
    @GetMapping("/create/admin")
    public String addServiceProductAdminForm(Principal principal, Model model) {
        var viewModel = new ServiceCreateViewModel(createBaseViewModel("Услуга"));
        var serviceViewModel = new ServiceProductCreateForm(null, null, null, null, null, null);
        var categoryViewModel = categoryService.findAllCategories().stream()
                .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
        var masterViewModel = userService.findAllMasters().stream()
                .map(master -> modelMapper.map(master, UserMasterViewModel.class)).collect(Collectors.toList());

        model.addAttribute("master", masterViewModel);
        model.addAttribute("category", categoryViewModel);
        model.addAttribute("form", serviceViewModel);
        model.addAttribute("model", viewModel);

        return "service-create";
    }

    @Override
    @PostMapping("/create/admin")
    public String addServiceProductAdmin(Principal principal,
            @Valid @ModelAttribute("form") ServiceProductCreateForm form, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var viewModel = new ServiceCreateViewModel(createBaseViewModel("Услуга"));
            var serviceViewModel = form;
            var categoryViewModel = categoryService.findAllCategories().stream()
                    .map(category -> modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
            var masterViewModel = userService.findAllMasters().stream()
                    .map(master -> modelMapper.map(master, UserMasterViewModel.class)).collect(Collectors.toList());

            model.addAttribute("master", masterViewModel);
            model.addAttribute("category", categoryViewModel);
            model.addAttribute("form", serviceViewModel);
            model.addAttribute("model", viewModel);

            return "service-create";
        }

        var serviceProduct = new ServiceProductRequestDTO(form.title(), form.description(), form.categoryId(),
                form.price(), form.createdById());
        serviceProductService.addServiceProduct(serviceProduct);

        return "redirect:/service/all/admin";
    }

    @Override
    @GetMapping("/update/master/{id}")
    public String updatesServiceForm(@PathVariable("id") UUID id, Model model) {
        var viewModel = new ServiceUpdateViewModel(createBaseViewModel("Обновление услуги"));
        var service = serviceProductService.findById(id);
        var serviceForm = new ServiceProductUpdateForm(id, service.getTitle(), service.getDescription(),
                service.getCategoryId(), service.getCategoryTitle(), service.getPrice(), service.getCreatedById());

        model.addAttribute("form", serviceForm);
        model.addAttribute("model", viewModel);

        return "service-update";
    }

    @Override
    @PostMapping("/update/master/{id}")
    public String updateService(@PathVariable("id") UUID id,
            @Valid @ModelAttribute("form") ServiceProductUpdateForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            var viewModel = new ServiceUpdateViewModel(createBaseViewModel("Обновление услуги"));
            var serviceForm = form;

            model.addAttribute("form", serviceForm);
            model.addAttribute("model", viewModel);

            return "service-update";
        }

        var serviceProduct = new ServiceProductRequestDTO(form.title(), form.description(), form.categoryId(),
                form.price(), form.createdById());

        serviceProductService.updateServiceProduct(id, serviceProduct);
        return "redirect:/service/all/master";
    }

    @Override
    @GetMapping("/update/admin/{id}")
    public String updatesServiceAdminForm(@PathVariable UUID id, Model model) {
        var viewModel = new ServiceUpdateViewModel(createBaseViewModel("Обновление услуги"));
        var service = serviceProductService.findById(id);
        var serviceForm = new ServiceProductUpdateForm(id, service.getTitle(), service.getDescription(),
                service.getCategoryId(), service.getCategoryTitle(), service.getPrice(), service.getCreatedById());

        model.addAttribute("form", serviceForm);
        model.addAttribute("model", viewModel);

        return "service-update";
    }

    @Override
    @PostMapping("/update/admin/{id}")
    public String updateServiceAdmin(@PathVariable UUID id,
            @Valid @ModelAttribute("form") ServiceProductUpdateForm form, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            var viewModel = new ServiceUpdateViewModel(createBaseViewModel("Обновление услуги"));
            var serviceForm = form;

            model.addAttribute("form", serviceForm);
            model.addAttribute("model", viewModel);

            return "service-update";
        }

        var serviceProduct = new ServiceProductRequestDTO(form.title(), form.description(), form.categoryId(),
                form.price(), form.createdById());

        serviceProductService.updateServiceProduct(id, serviceProduct);
        return "redirect:/service/all/admin";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
