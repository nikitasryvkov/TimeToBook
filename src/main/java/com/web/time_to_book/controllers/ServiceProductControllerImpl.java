package com.web.time_to_book.controllers;

import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.ServiceProductController;
import com.example.demo.forms.done.ServiceProductCreateForm;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceCreateViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductsViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceViewModel;
import com.web.time_to_book.dtos.request.ServiceProductRequestDTO;
import com.web.time_to_book.services.ServiceProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/service")
public class ServiceProductControllerImpl implements ServiceProductController {

    private ServiceProductService serviceProductService;
    private ModelMapper modelMapper;

    @Autowired
    public void setServiceProductControllerImpl(ServiceProductService serviceProductService, ModelMapper modelMapper) {
        this.serviceProductService = serviceProductService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/all")
    public String getServiceProducts(Model model) {
        var serviceViewModel = serviceProductService.findAllServices().stream()
                .map(service -> modelMapper.map(service, ServiceProductsViewModel.class)).collect(Collectors.toList());
        var viewModel = new ServiceViewModel(createBaseViewModel("Услуги"), serviceViewModel);

        model.addAttribute("model", viewModel);
        return "services";
    }

    @Override
    @GetMapping("/{id}")
    public String getServiceProduct(@PathVariable("id") UUID id, Model model) {
        var serviceProductResponseDTO = serviceProductService.findById(id);
        var viewModel = new ServiceProductViewModel(createBaseViewModel("Услуга"),
                serviceProductResponseDTO.getId(), serviceProductResponseDTO.getTitle(),
                serviceProductResponseDTO.getDescription(), serviceProductResponseDTO.getCategoryId(),
                serviceProductResponseDTO.getCategoryTitle(), serviceProductResponseDTO.getPrice(),
                serviceProductResponseDTO.getCreatedById(), serviceProductResponseDTO.getCreatedByName());

        model.addAttribute("model", viewModel);
        return "service";
    }

    @Override
    @GetMapping("/create")
    public String addServiceProductForm(Model model) {
        var serviceViewModel = new ServiceProductCreateForm("", "", null, null, null);
        var viewModel = new ServiceCreateViewModel(createBaseViewModel("Услуга"), serviceViewModel);
        model.addAttribute("model", viewModel);
        return "service-create";
    }

    @Override
    @PostMapping("/create")
    public String addServiceProduct(@Valid ServiceProductCreateForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var serviceViewModel = new ServiceProductCreateForm("", "", null, null, null);
            var viewModel = new ServiceCreateViewModel(createBaseViewModel("Услуга"), serviceViewModel);
            model.addAttribute("model", viewModel);
            return "service-create";
        }

        ServiceProductRequestDTO service = new ServiceProductRequestDTO(form.title(), form.description(),
                form.categoryId(), form.price(), form.createdById());
        serviceProductService.addServiceProduct(service);
        return "redirect:/";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
