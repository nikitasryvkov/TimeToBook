package com.web.time_to_book.utils.mapper;

import org.modelmapper.ModelMapper;

import com.example.demo.viewmodel.appointment.AppointmentViewModel;
import com.example.demo.viewmodel.category.CategoryViewModel;
import com.example.demo.viewmodel.feedback.FeedbackViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductViewModel;
import com.example.demo.viewmodel.serviceProduct.ServiceProductsViewModel;
import com.example.demo.viewmodel.user.UserMasterViewModel;
import com.web.time_to_book.dtos.CategoryDTO;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.dtos.response.AppointmentDTO;
import com.web.time_to_book.dtos.response.AppointmentResponseDTO;
import com.web.time_to_book.dtos.response.FeedbackDTO;
import com.web.time_to_book.dtos.response.FeedbackResponseDTO;
import com.web.time_to_book.dtos.response.ServiceProductDTO;
import com.web.time_to_book.dtos.response.ServiceProductResponseDTO;
import com.web.time_to_book.dtos.response.UserResponseDTO;
import com.web.time_to_book.models.Appointment;
import com.web.time_to_book.models.Category;
import com.web.time_to_book.models.Feedback;
import com.web.time_to_book.models.ServiceProduct;
import com.web.time_to_book.models.User;

public class ModelMapperFactory {

    private ModelMapper modelMapper;

    public ModelMapper getMapper() {
        if (modelMapper == null) {
            modelMapper = initializeModelMapper(new ModelMapper());
        }

        return modelMapper;
    }

    private ModelMapper initializeModelMapper(ModelMapper modelMapper) {

        modelMapper
                .typeMap(User.class, UserRequestDTO.class)
                .addMappings(mapper -> {
                    mapper.map(user -> user.getId(), UserRequestDTO::setId);
                    mapper.map(user -> user.getFirstName(), UserRequestDTO::setFirstName);
                    mapper.map(user -> user.getLastName(), UserRequestDTO::setLastName);
                    mapper.map(user -> user.getUsername(), UserRequestDTO::setUsername);
                    mapper.map(user -> user.getEmail(), UserRequestDTO::setEmail);
                    mapper.map(user -> user.getPassword(), UserRequestDTO::setPassword);
                    mapper.map(user -> user.getPhoneNumber(), UserRequestDTO::setPhoneNumber);
                    mapper.map(user -> user.getAvatarURL(), UserRequestDTO::setAvatarURL);
                });

        modelMapper
                .typeMap(User.class, UserResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(user -> user.getFirstName(), UserResponseDTO::setFirstName);
                    mapper.map(user -> user.getLastName(), UserResponseDTO::setLastName);
                    mapper.map(user -> user.getUsername(), UserResponseDTO::setUsername);
                    mapper.map(user -> user.getEmail(), UserResponseDTO::setEmail);
                    mapper.map(user -> user.getPassword(), UserResponseDTO::setPassword);
                    mapper.map(user -> user.getPhoneNumber(), UserResponseDTO::setPhoneNumber);
                    mapper.map(user -> user.getAvatarURL(), UserResponseDTO::setAvatarURL);
                    mapper.map(user -> user.getPassword(), UserResponseDTO::setPassword);
                    mapper.map(user -> user.getNumberOfAppointments(), UserResponseDTO::setNumberOfAppointments);
                });

        modelMapper
                .typeMap(UserRequestDTO.class, UserResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(user -> user.getId(), UserResponseDTO::setId);
                    mapper.map(user -> user.getFirstName(), UserResponseDTO::setFirstName);
                    mapper.map(user -> user.getLastName(), UserResponseDTO::setLastName);
                    mapper.map(user -> user.getUsername(), UserResponseDTO::setUsername);
                    mapper.map(user -> user.getEmail(), UserResponseDTO::setEmail);
                    mapper.map(user -> user.getPassword(), UserResponseDTO::setPassword);
                    mapper.map(user -> user.getPhoneNumber(), UserResponseDTO::setPhoneNumber);
                    mapper.map(user -> user.getAvatarURL(), UserResponseDTO::setAvatarURL);
                });

        modelMapper
                .typeMap(UserResponseDTO.class, UserMasterViewModel.class)
                .addMappings(mapper -> {
                    mapper.map(user -> user.getId(), UserMasterViewModel::setId);
                    mapper.map(user -> user.getFirstName(), UserMasterViewModel::setUserFirstName);
                });

        modelMapper
                .typeMap(AppointmentDTO.class, AppointmentViewModel.class)
                .addMappings(mapper -> {
                    mapper.map(appointment -> appointment.getId(), AppointmentViewModel::setId);
                    mapper.map(appointment -> appointment.getCreatedAt(), AppointmentViewModel::setCreatedAt);
                    mapper.map(appointment -> appointment.getRecordTime(), AppointmentViewModel::setRecordTime);
                    mapper.map(appointment -> appointment.getClientId(), AppointmentViewModel::setClientId);
                    mapper.map(appointment -> appointment.getClientFirstName(), AppointmentViewModel::setClientName);
                    mapper.map(appointment -> appointment.getMasterId(), AppointmentViewModel::setMasterId);
                    mapper.map(appointment -> appointment.getMasterFirstName(), AppointmentViewModel::setMasterName);
                    mapper.map(appointment -> appointment.getServiceId(), AppointmentViewModel::setServiceId);
                    mapper.map(appointment -> appointment.getServiceTitle(), AppointmentViewModel::setServiceTitle);
                    mapper.map(appointment -> appointment.getStatus(), AppointmentViewModel::setStatus);
                });

        modelMapper
                .typeMap(ServiceProductResponseDTO.class, ServiceProductsViewModel.class)
                .addMappings(mapper -> {
                    mapper.map(service -> service.getId(), ServiceProductsViewModel::setId);
                    mapper.map(service -> service.getTitle(), ServiceProductsViewModel::setTitle);
                });

        modelMapper
                .typeMap(ServiceProduct.class, ServiceProductDTO.class)
                .addMappings(mapper -> {
                    mapper.map(service -> service.getId(), ServiceProductDTO::setId);
                    mapper.map(service -> service.getTitle(), ServiceProductDTO::setTitle);
                    mapper.map(service -> service.getDescription(), ServiceProductDTO::setDescription);
                    mapper.map(service -> service.getCategory().getId(), ServiceProductDTO::setCategoryId);
                    mapper.map(service -> service.getCategory().getName(), ServiceProductDTO::setCategoryTitle);
                    mapper.map(service -> service.getPrice(), ServiceProductDTO::setPrice);
                    mapper.map(service -> service.getCreatedBy().getId(), ServiceProductDTO::setCreatedById);
                    mapper.map(service -> service.getCreatedBy().getFirstName(),
                            ServiceProductDTO::setCreatedByFirstName);
                });

        modelMapper
                .typeMap(ServiceProductDTO.class, ServiceProductViewModel.class)
                .addMappings(mapper -> {
                    mapper.map(service -> service.getId(), ServiceProductViewModel::setId);
                    mapper.map(service -> service.getTitle(), ServiceProductViewModel::setTitle);
                    mapper.map(service -> service.getDescription(), ServiceProductViewModel::setDescription);
                    mapper.map(service -> service.getCategoryId(), ServiceProductViewModel::setCategoryId);
                    mapper.map(service -> service.getCategoryTitle(), ServiceProductViewModel::setCategoryTitle);
                    mapper.map(service -> service.getPrice(), ServiceProductViewModel::setPrice);
                    mapper.map(service -> service.getCreatedById(), ServiceProductViewModel::setCreatedById);
                    mapper.map(service -> service.getCreatedByFirstName(), ServiceProductViewModel::setCreatedByName);
                });

        modelMapper
                .typeMap(Category.class, CategoryDTO.class)
                .addMappings(mapper -> {
                    mapper.map(category -> category.getId(), CategoryDTO::setId);
                    mapper.map(category -> category.getName(), CategoryDTO::setName);
                });

        modelMapper
                .typeMap(CategoryDTO.class, CategoryViewModel.class)
                .addMappings(mapper -> {
                    mapper.map(category -> category.getId(), CategoryViewModel::setId);
                    mapper.map(category -> category.getName(), CategoryViewModel::setName);
                });

        modelMapper
                .typeMap(Appointment.class, AppointmentResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(appointment -> appointment.getCreatedAt(), AppointmentResponseDTO::setCreatedAt);
                    mapper.map(appointment -> appointment.getRecordTime(), AppointmentResponseDTO::setRecordTime);
                    mapper.map(appointment -> appointment.getClient().getId(), AppointmentResponseDTO::setClientId);
                    mapper.map(appointment -> appointment.getMaster().getId(), AppointmentResponseDTO::setMasterId);
                    mapper.map(appointment -> appointment.getService().getId(), AppointmentResponseDTO::setServiceId);
                    mapper.map(appointment -> appointment.getStatus(), AppointmentResponseDTO::setStatus);
                });

        modelMapper
                .typeMap(Feedback.class, FeedbackDTO.class)
                .addMappings(mapper -> {
                    mapper.map(feedback -> feedback.getId(), FeedbackDTO::setId);
                    mapper.map(feedback -> feedback.getCreatedAt(), FeedbackDTO::setCreatedAt);
                    mapper.map(feedback -> feedback.getText(), FeedbackDTO::setText);
                    mapper.map(feedback -> feedback.getAppointment().getId(), FeedbackDTO::setAppointmentId);
                    mapper.map(feedback -> feedback.getAppointment().getService().getTitle(),
                            FeedbackDTO::setTitle);
                    mapper.map(feedback -> feedback.getAppointment().getClient().getId(), FeedbackDTO::setCreatedById);
                    mapper.map(feedback -> feedback.getAppointment().getClient().getFirstName(),
                            FeedbackDTO::setCreatedByFirstName);
                    mapper.map(feedback -> feedback.getAppointment().getMaster().getId(), FeedbackDTO::setMasterId);
                    mapper.map(feedback -> feedback.getAppointment().getMaster().getFirstName(),
                            FeedbackDTO::setMasterFirstName);
                    mapper.map(feedback -> feedback.getEstimation(), FeedbackDTO::setEstimation);
                });

        modelMapper
                .typeMap(FeedbackDTO.class, FeedbackViewModel.class)
                .addMappings(mapper -> {
                    mapper.map(feedback -> feedback.getId(), FeedbackViewModel::setId);
                    mapper.map(feedback -> feedback.getCreatedAt(), FeedbackViewModel::setCreatedAt);
                    mapper.map(feedback -> feedback.getText(), FeedbackViewModel::setText);
                    mapper.map(feedback -> feedback.getAppointmentId(), FeedbackViewModel::setAppointmentId);
                    mapper.map(feedback -> feedback.getTitle(),
                            FeedbackViewModel::setTitle);
                    mapper.map(feedback -> feedback.getCreatedById(), FeedbackViewModel::setCreatedById);
                    mapper.map(feedback -> feedback.getCreatedByFirstName(),
                            FeedbackViewModel::setCreatedByFirstName);
                    mapper.map(feedback -> feedback.getMasterId(), FeedbackViewModel::setMasterId);
                    mapper.map(feedback -> feedback.getMasterFirstName(),
                            FeedbackViewModel::setMasterFirstName);
                    mapper.map(feedback -> feedback.getEstimation(), FeedbackViewModel::setEstimation);
                });

        return modelMapper;
    }
}
