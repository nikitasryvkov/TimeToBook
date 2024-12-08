package com.web.time_to_book.controllers;

import java.time.LocalDateTime;
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

import com.example.demo.controllers.done.AppointmentController;
import com.example.demo.forms.done.AppointmentCreateForm;
import com.example.demo.forms.done.AppointmentUpdateForm;
import com.example.demo.viewmodel.appointment.AppointmentCreateViewModel;
import com.example.demo.viewmodel.appointment.AppointmentUpdateViewModel;
import com.example.demo.viewmodel.appointment.AppointmentViewModel;
import com.example.demo.viewmodel.appointment.AppointmentsViewModel;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.web.time_to_book.dtos.request.AppointmentRequestDTO;
import com.web.time_to_book.services.AppointmentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/appointment")
public class AppointmentControllerImpl implements AppointmentController {

    private AppointmentService appointmentService;
    private ModelMapper modelMapper;

    @Autowired
    public void setAppointmentControllerImpl(AppointmentService appointmentService, ModelMapper modelMapper) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/{id}")
    public String getAppointment(@PathVariable("id") UUID id, Model model) {
        var appointmentResponseDTO = appointmentService.findById(id);
        var viewModel = new AppointmentViewModel(createBaseViewModel("Запись"),
                appointmentResponseDTO.getId(), appointmentResponseDTO.getCreatedAt(),
                appointmentResponseDTO.getRecordTime(), appointmentResponseDTO.getClientId(),
                appointmentResponseDTO.getClientName(), appointmentResponseDTO.getMasterId(),
                appointmentResponseDTO.getMasterName(), appointmentResponseDTO.getServiceId(),
                appointmentResponseDTO.getServiceTitle(), appointmentResponseDTO.getStatus());
        model.addAttribute("model", viewModel);
        return "appointment";
    }

    @Override
    @GetMapping("/all/{id}")
    public String getAppointments(@PathVariable("id") UUID id, Model model) {
        var appointmentByIdViewModel = appointmentService.findAllAppointmentsById(id).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentViewModel.class))
                .collect(Collectors.toList());
        var viewModel = new AppointmentsViewModel(createBaseViewModel("Записи"), appointmentByIdViewModel);

        model.addAttribute("model", viewModel);
        return "appointments";
    }

    @Override
    @GetMapping("/create")
    public String createAppointmentForm(Model model) {
        var appointmentViewModel = new AppointmentCreateForm(LocalDateTime.now(), null, null, null, null, null);
        var viewModel = new AppointmentCreateViewModel(createBaseViewModel("Запись"), appointmentViewModel);
        model.addAttribute("model", viewModel);
        return "appointment-create";
    }

    @Override
    @PostMapping("/create")
    public String createAppointment(@Valid AppointmentCreateForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var appointmentViewModel = new AppointmentCreateForm(LocalDateTime.now(), null, null, null, null, null);
            var viewModel = new AppointmentCreateViewModel(createBaseViewModel("Запись"), appointmentViewModel);
            model.addAttribute("model", viewModel);
            return "appointment-create";
        }

        AppointmentRequestDTO appointment = new AppointmentRequestDTO(LocalDateTime.now(), form.recordTime(),
                form.clientId(), form.masterId(), form.serviceId(), form.status());
        appointmentService.addAppointment(appointment);
        return "redirect:/appointment/all";
    }

    @Override
    @GetMapping("/{id}/update")
    public String updateAppointmentForm(@PathVariable("id") UUID id, Model model) {
        LocalDateTime now = LocalDateTime.now();
        var updateViewModel = new AppointmentUpdateForm(now);
        var viewModel = new AppointmentUpdateViewModel(createBaseViewModel("Изменить запись"), updateViewModel);
        model.addAttribute("model", viewModel);
        return "appointment-update";
    }

    @Override
    @PostMapping("/{id}/update")
    public String updateAppointment(@Valid @PathVariable("id") UUID id, AppointmentUpdateForm form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            LocalDateTime now = LocalDateTime.now();
            var updateViewModel = new AppointmentUpdateForm(now);
            var viewModel = new AppointmentUpdateViewModel(createBaseViewModel("Изменить запись"), updateViewModel);
            model.addAttribute("model", viewModel);
            return "appointment-update";
        }

        appointmentService.updateAppointment(id, new AppointmentRequestDTO(form.recordTime()));
        return "redirect:/appointment/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
