package com.web.time_to_book.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.done.FeedbackController;
import com.example.demo.forms.done.FeedbackCreateForm;
import com.example.demo.forms.done.FeedbackUpdateForm;
import com.example.demo.viewmodel.appointment.AppointmentViewModel;
import com.example.demo.viewmodel.feedback.FeedbackCreateViewModel;
import com.example.demo.viewmodel.feedback.FeedbackUpdateViewModel;
import com.example.demo.viewmodel.feedback.FeedbackViewModel;
import com.example.demo.viewmodel.feedback.FeedbacksViewModel;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductViewModel;
import com.web.time_to_book.dtos.request.FeedbackRequestDTO;
import com.web.time_to_book.models.enums.AppointmentStatusEnum;
import com.web.time_to_book.services.AppointmentService;
import com.web.time_to_book.services.FeedbackService;
import com.web.time_to_book.services.ServiceProductService;
import com.web.time_to_book.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/feedback")
public class FeedbackControllerImpl implements FeedbackController {

    private FeedbackService feedbackService;
    private UserService userService;
    private AppointmentService appointmentService;
    private ServiceProductService serviceProductService;
    private ModelMapper modelMapper;

    @Autowired
    public void setFeedbackControllerImpl(FeedbackService feedbackService, UserService userService,
            AppointmentService appointmentService, ModelMapper modelMapper,
            ServiceProductService serviceProductService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
        this.serviceProductService = serviceProductService;
    }

    @Override
    @GetMapping("/{id}")
    public String getFeedback(@PathVariable("id") UUID id, Model model) {
        var feedbackDTO = feedbackService.findById(id);
        var viewModel = new FeedbackViewModel(createBaseViewModel("Отзыв"), feedbackDTO.getCreatedAt(),
                feedbackDTO.getText(), feedbackDTO.getAppointmentId(), feedbackDTO.getTitle(),
                feedbackDTO.getCreatedById(), feedbackDTO.getCreatedByFirstName(), feedbackDTO.getMasterId(),
                feedbackDTO.getMasterFirstName(), feedbackDTO.getEstimation());
        model.addAttribute("model", viewModel);
        return "feedback";
    }

    @Override
    @GetMapping("/all")
    public String getFeedbacks(Principal principal, Model model) {
        var user = userService.findByUsername(principal.getName());
        var feedbackViewModel = feedbackService.findAllFeedbacksByUserId(user.getId()).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackViewModel.class)).collect(Collectors.toList());
        var serviceViewModel = serviceProductService
                .getServicesByStatusAndUser(user.getId(), AppointmentStatusEnum.DONE).stream()
                .map(service -> modelMapper.map(service, ServiceProductViewModel.class)).collect(Collectors.toList());

        // for (var feed : feedbackViewModel) {
        // System.out.println(feed.getTitle());
        // }
        var viewModel = new FeedbacksViewModel(createBaseViewModel("Отзывы"));
        model.addAttribute("service", serviceViewModel);
        model.addAttribute("feedback", feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "feedbacks";
    }

    @Override
    @GetMapping("/all/master")
    public String getFeedbacksMaster(Principal principal, Model model) {
        var user = userService.findByUsername(principal.getName());
        var feedbackViewModel = feedbackService.findAllFeedbacksByMasterId(user.getId()).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackViewModel.class)).collect(Collectors.toList());
        var viewModel = new FeedbacksViewModel(createBaseViewModel("Отзывы"));
        model.addAttribute("feedback", feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "feedbacks";
    }

    @Override
    @GetMapping("/all/admin")
    public String getFeedbacksAdmin(Model model) {
        var feedbackViewModel = feedbackService.findAllFeedbacks().stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackViewModel.class)).collect(Collectors.toList());
        var viewModel = new FeedbacksViewModel(createBaseViewModel("Отзывы"));
        System.out.println(feedbackViewModel.get(0).getTitle());
        model.addAttribute("feedback", feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "feedbacks";
    }

    @Override
    @GetMapping("/create")
    public String createFeedbackForm(Principal principal, Model model) {
        var viewModel = new FeedbackCreateViewModel(createBaseViewModel("Отзыв"));
        var feedbackViewModel = new FeedbackCreateForm(LocalDateTime.now(), null, null, null, null);
        var client = userService.findByUsername(principal.getName());
        var appointmentViewModel = appointmentService.findAllByUserAndStatus(client.getId(), AppointmentStatusEnum.DONE)
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentViewModel.class))
                .collect(Collectors.toList());
        var serviceViewModel = serviceProductService
                .getServicesByStatusAndUser(client.getId(), AppointmentStatusEnum.DONE).stream()
                .map(service -> modelMapper.map(service, ServiceProductViewModel.class)).collect(Collectors.toList());

        model.addAttribute("service", serviceViewModel);
        model.addAttribute("appointment", appointmentViewModel);
        model.addAttribute("feedback", feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "feedback-create";
    }

    @Override
    @PostMapping("/create")
    public String createFeedback(Principal principal, @Valid @ModelAttribute("form") FeedbackCreateForm form,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            for (var error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            var viewModel = new FeedbackCreateViewModel(createBaseViewModel("Отзыв"));
            var feedbackViewModel = form;
            var client = userService.findByUsername(principal.getName());
            var appointmentViewModel = appointmentService.findAllByUserId(client.getId());
            var serviceViewModel = serviceProductService
                    .getServicesByStatusAndUser(client.getId(), AppointmentStatusEnum.DONE).stream()
                    .map(service -> modelMapper.map(service, ServiceProductViewModel.class))
                    .collect(Collectors.toList());

            model.addAttribute("service", serviceViewModel);
            model.addAttribute("appointment", appointmentViewModel);
            model.addAttribute("feedback", feedbackViewModel);
            model.addAttribute("model", viewModel);
            return "feedback-create";
        }
        FeedbackRequestDTO feedback = new FeedbackRequestDTO(form.text(), form.appointmentId(), form.estimation());
        feedbackService.addFeedback(feedback);

        return "redirect:/feedback/all";
    }

    @Override
    @GetMapping("/{id}/update")
    public String updateFeedbackForm(@PathVariable("id") UUID id, Model model) {
        var feedbackViewModel = new FeedbackUpdateForm("", null);
        var viewModel = new FeedbackUpdateViewModel(createBaseViewModel("Отзыв"), feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "feedback-update";
    }

    @Override
    @PostMapping("/{id}/update")
    public String updateFeedback(@PathVariable("id") UUID id, @Valid @ModelAttribute("form") FeedbackUpdateForm form,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var feedbackViewModel = new FeedbackUpdateForm("", null);
            var viewModel = new FeedbackUpdateViewModel(createBaseViewModel("Отзыв"), feedbackViewModel);
            model.addAttribute("model", viewModel);
            return "feedback-update";
        }

        feedbackService.updateFeedback(id, new FeedbackRequestDTO(form.text(), form.estimation()));
        return "redirect:/feedback/" + id;
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
