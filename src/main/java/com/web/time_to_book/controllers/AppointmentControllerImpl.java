package com.web.time_to_book.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.AppointmentController;
import com.example.demo.forms.done.AppointmentCreateForm;
import com.example.demo.forms.done.AppointmentUpdateAdminForm;
import com.example.demo.forms.done.AppointmentUpdateForm;
import com.example.demo.viewmodel.appointment.AppointmentCreateViewModel;
import com.example.demo.viewmodel.appointment.AppointmentUpdateViewModel;
import com.example.demo.viewmodel.appointment.AppointmentViewModel;
import com.example.demo.viewmodel.appointment.AppointmentsViewModel;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductViewModel;
import com.example.demo.viewmodel.user.UserMasterViewModel;
import com.web.time_to_book.dtos.request.AppointmentRequestDTO;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;
import com.web.time_to_book.services.AppointmentService;
import com.web.time_to_book.services.ServiceProductService;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/appointment")
public class AppointmentControllerImpl implements AppointmentController {

    private AppointmentService appointmentService;
    private UserService userService;
    private ServiceProductService serviceProductService;
    private ModelMapper modelMapper;

    @Autowired
    public void setAppointmentControllerImpl(AppointmentService appointmentService, UserService userService,
            ServiceProductService serviceProductService, ModelMapper modelMapper) {
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.serviceProductService = serviceProductService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/{id}")
    public String getAppointment(@PathVariable("id") UUID id, Model model) {
        var appointmentResponseDTO = appointmentService.findById(id);
        var viewModel = new AppointmentViewModel(createBaseViewModel("Запись"),
                appointmentResponseDTO.getId(), appointmentResponseDTO.getCreatedAt(),
                appointmentResponseDTO.getRecordTime(), appointmentResponseDTO.getClientId(),
                appointmentResponseDTO.getClientFirstName(), appointmentResponseDTO.getMasterId(),
                appointmentResponseDTO.getMasterFirstName(), appointmentResponseDTO.getServiceId(),
                appointmentResponseDTO.getServiceTitle(), appointmentResponseDTO.getStatus());
        model.addAttribute("model", viewModel);
        return "appointment";
    }

    @Override
    @GetMapping("/all")
    public String getAppointments(Principal principal, Model model) {
        var user = userService.findByUsername(principal.getName());
        var appointmentByIdViewModel = appointmentService.findAllByUserId(user.getId()).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentViewModel.class))
                .collect(Collectors.toList());
        var viewModel = new AppointmentsViewModel(createBaseViewModel("Записи"));

        model.addAttribute("appointments", appointmentByIdViewModel);
        model.addAttribute("model", viewModel);
        return "appointments";
    }

    @Override
    @GetMapping("/all/master")
    public String getAppointmentsMaster(Principal principal, Model model) {
        var user = userService.findByUsername(principal.getName());
        var appointmentByIdViewModel = appointmentService.findAllByMasterId(user.getId()).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentViewModel.class))
                .collect(Collectors.toList());
        var viewModel = new AppointmentsViewModel(createBaseViewModel("Записи"));

        model.addAttribute("appointments", appointmentByIdViewModel);
        model.addAttribute("model", viewModel);
        return "appointments";
    }

    @Override
    @GetMapping("/all/admin")
    public String getAppointmentsAdmin(Model model) {
        var appointmentByIdViewModel = appointmentService.findAllAppointments().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentViewModel.class))
                .collect(Collectors.toList());
        var viewModel = new AppointmentsViewModel(createBaseViewModel("Записи"));

        model.addAttribute("appointments", appointmentByIdViewModel);
        model.addAttribute("model", viewModel);
        return "appointments";
    }

    @Override
    @GetMapping("/create/{id}")
    public String createAppointmentForm(Principal principal, @PathVariable("id") UUID id, Model model) {
        var service = modelMapper.map(serviceProductService.findById(id), ServiceProductViewModel.class);
        var client = userService.findByUsername(principal.getName());
        var viewModel = new AppointmentCreateViewModel(createBaseViewModel("Запись"));
        var masterViewModel = userService.findAllMasters().stream()
                .map(masters -> modelMapper.map(masters, UserMasterViewModel.class)).collect(Collectors.toList());

        var appointmentForm = new AppointmentCreateForm(LocalDateTime.now(), null, client.getFirstName(), null,
                null, id, service.getTitle());

        model.addAttribute("master", masterViewModel);
        model.addAttribute("form", appointmentForm);
        model.addAttribute("model", viewModel);
        model.addAttribute("service", service);

        return "appointment-create";
    }

    @Override
    @PostMapping("/create/{id}")
    public String createAppointment(Principal principal, @PathVariable("id") UUID id,
            @Valid @ModelAttribute("form") AppointmentCreateForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var masterViewModel = userService
                    .findAllMasters()
                    .stream()
                    .map(masters -> modelMapper.map(masters, UserMasterViewModel.class))
                    .collect(Collectors.toList());
            var service = modelMapper
                    .map(serviceProductService.findById(id), ServiceProductViewModel.class);

            model.addAttribute("model", new AppointmentCreateViewModel(createBaseViewModel("Запись")));
            model.addAttribute("master", masterViewModel);
            model.addAttribute("form", form);
            model.addAttribute("service", service);

            return "appointment-create";
        }

        var client = userService.findByUsername(principal.getName());

        AppointmentRequestDTO appointment = new AppointmentRequestDTO(
                LocalDateTime.now(),
                form.recordTime(),
                client.getId(),
                form.masterId(),
                id);

        appointmentService.addAppointment(appointment);
        return "redirect:/appointment/all";
    }

    @Override
    @GetMapping("/update/master/{id}")
    public String updateAppointmentForm(@PathVariable("id") UUID id, Model model) {
        var viewModel = new AppointmentUpdateViewModel(createBaseViewModel("Изменить запись"));
        var appointment = appointmentService.findById(id);
        var appointmentForm = new AppointmentUpdateForm(id, appointment.getCreatedAt(), appointment.getRecordTime(),
                appointment.getStatus());

        model.addAttribute("form", appointmentForm);
        model.addAttribute("model", viewModel);
        return "appointment-update";
    }

    @Override
    @PostMapping("/update/master/{id}")
    public String updateAppointment(@PathVariable("id") UUID id,
            @Valid @ModelAttribute("form") AppointmentUpdateForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var viewModel = new AppointmentUpdateViewModel(createBaseViewModel("Изменить запись"));
            var appointmentForm = form;

            model.addAttribute("form", appointmentForm);
            model.addAttribute("model", viewModel);
            return "appointment-update";
        }

        var appointmentBy = appointmentService.findById(id);

        var appointment = new AppointmentRequestDTO(
                form.createdAt(),
                form.recordTime(),
                appointmentBy.getClientId(),
                appointmentBy.getMasterId(),
                appointmentBy.getServiceId(),
                form.status());

        appointmentService.updateAppointment(id, appointment);
        return "redirect:/appointment/all/master";
    }

    @Override
    @GetMapping("/update/admin/{id}")
    public String updateAppointmentAdminForm(@PathVariable("id") UUID id, Model model) {
        var viewModel = new AppointmentUpdateViewModel(createBaseViewModel("Изменить запись"));
        var appointment = appointmentService.findById(id);
        var appointmentForm = new AppointmentUpdateAdminForm(id, appointment.getRecordTime(), appointment.getMasterId(),
                appointment.getMasterFirstName(), appointment.getStatus());

        model.addAttribute("form", appointmentForm);
        model.addAttribute("model", viewModel);
        return "appointment-update";
    }

    @Override
    @PostMapping("/update/admin/{id}")
    public String updateAppointmentAdmin(@PathVariable("id") UUID id,
            @Valid @ModelAttribute("form") AppointmentUpdateForm form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var viewModel = new AppointmentUpdateViewModel(createBaseViewModel("Изменить запись"));
            var appointmentForm = form;

            model.addAttribute("form", appointmentForm);
            model.addAttribute("model", viewModel);
            return "appointment-update";
        }

        var appointmentBy = appointmentService.findById(id);

        var appointment = new AppointmentRequestDTO(
                form.createdAt(),
                form.recordTime(),
                appointmentBy.getClientId(),
                appointmentBy.getMasterId(),
                appointmentBy.getServiceId(),
                form.status());

        appointmentService.updateAppointment(id, appointment);
        return "redirect:/appointment/all/admin";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
