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

import com.example.demo.controllers.done.FeedbackController;
import com.example.demo.forms.done.FeedbackCreateForm;
import com.example.demo.forms.done.FeedbackUpdateForm;
import com.example.demo.viewmodel.feedback.FeedbackCreateViewModel;
import com.example.demo.viewmodel.feedback.FeedbackUpdateViewModel;
import com.example.demo.viewmodel.feedback.FeedbackViewModel;
import com.example.demo.viewmodel.feedback.FeedbacksViewModel;
import com.example.demo.viewmodel.pages.BaseViewModel;
import com.web.time_to_book.dtos.request.FeedbackRequestDTO;
import com.web.time_to_book.services.FeedbackService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/feedback")
public class FeedbackControllerImpl implements FeedbackController {

    private FeedbackService feedbackService;
    private ModelMapper modelMapper;

    @Autowired
    public void setFeedbackControllerImpl(FeedbackService feedbackService, ModelMapper modelMapper) {
        this.feedbackService = feedbackService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/{id}")
    public String getFeedback(@PathVariable("id") UUID id, Model model) {
        var feedbackResponseDTO = feedbackService.findById(id);
        var viewModel = new FeedbackViewModel(createBaseViewModel("Отзыв"), feedbackResponseDTO.getCreatedAt(),
                feedbackResponseDTO.getText(), feedbackResponseDTO.getServiceId(),
                feedbackResponseDTO.getServiceTitle(), feedbackResponseDTO.getCreatedById(),
                feedbackResponseDTO.getCreatedByName(), feedbackResponseDTO.getEstimation());
        model.addAttribute("model", viewModel);
        return "feedback";
    }

    @Override
    @GetMapping("/all/{id}")
    public String getFeedbacks(@PathVariable("id") UUID id, Model model) {
        var feedbackViewModel = feedbackService.findAllFeedbacksById(id).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackViewModel.class)).collect(Collectors.toList());
        var viewModel = new FeedbacksViewModel(createBaseViewModel("Отзывы"), feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "feedbacks";
    }

    @Override
    @GetMapping("/create")
    public String createFeedbackForm(Model model) {
        var feedbackViewModel = new FeedbackCreateForm(LocalDateTime.now(), "", null, null, null);
        var viewModel = new FeedbackCreateViewModel(createBaseViewModel("Отзыв"), feedbackViewModel);
        model.addAttribute("model", viewModel);
        return "feedback-create";
    }

    @Override
    @PostMapping("/create")
    public String createFeedback(@Valid FeedbackCreateForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var feedbackViewModel = new FeedbackCreateForm(LocalDateTime.now(), "", null, null, null);
            var viewModel = new FeedbackCreateViewModel(createBaseViewModel("Отзыв"), feedbackViewModel);
            model.addAttribute("model", viewModel);
            return "feedback-create";
        }

        FeedbackRequestDTO feedback = new FeedbackRequestDTO(form.text(), form.serviceId(), form.createdById(),
                form.estimation());
        feedbackService.addFeedback(feedback);
        return "redirect:/";
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
    public String updateFeedback(@Valid @PathVariable("id") UUID id, FeedbackUpdateForm form,
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
