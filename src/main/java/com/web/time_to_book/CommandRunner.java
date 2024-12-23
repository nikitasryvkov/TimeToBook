package com.web.time_to_book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.web.time_to_book.dtos.CategoryDTO;
import com.web.time_to_book.dtos.RoleDTO;
import com.web.time_to_book.dtos.request.ServiceProductRequestDTO;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.models.Role;
import com.web.time_to_book.models.User;
import com.web.time_to_book.models.enums.UserRoles;
import com.web.time_to_book.repositories.RoleRepository;
import com.web.time_to_book.repositories.UserRepository;
import com.web.time_to_book.services.CategoryService;
import com.web.time_to_book.services.RoleService;
import com.web.time_to_book.services.ServiceProductService;
import com.web.time_to_book.services.UserService;

@Component
public class CommandRunner implements CommandLineRunner {

    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final RoleService roleService;
    private RoleRepository roleRepository;
    private final UserService userService;
    private UserRepository userRepository;
    private final CategoryService categoryService;
    private final ServiceProductService serviceProductService;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public CommandRunner(RoleService roleService, UserService userService, UserRepository userRepository,
            CategoryService categoryService,
            ServiceProductService serviceProductService, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.roleService = roleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.serviceProductService = serviceProductService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start!");

        initRoles();
        // initUsers();
        // addService();
        // addUser();
        // addCategory();
    }

    private void initRoles() {
        if (roleService.count() == 0) {
            var moderatorRole = new RoleDTO(UserRoles.MODERATOR);
            var adminRole = new RoleDTO(UserRoles.ADMIN);
            var normalUserRole = new RoleDTO(UserRoles.USER);
            roleService.save(moderatorRole);
            roleService.save(adminRole);
            roleService.save(normalUserRole);
        }
    }

    private void initUsers() {
        if (userRepository.findAll().isEmpty()) {
            initAdmin();
            initModerator();
            initUser();
        }
    }

    private void initAdmin() {
        var adminRole = roleRepository.findByName(UserRoles.ADMIN).orElseThrow();

        var adminUser = new User("AdminFirstName", "AdminLastName", "AdminUserName", "AdminEmail",
                passwordEncoder.encode("AdminPassword"),
                "AdminPhoneNumber");
        adminUser.setRoles(List.of(adminRole));

        userRepository.save(adminUser);
    }

    private void initModerator() {
        var moderatorRole = roleRepository.findByName(UserRoles.MODERATOR).orElseThrow();

        var moderatorUser = new User("ModeratorFirstName", "ModeratorLastName", "ModeratorUserName", "ModeratorEmail",
                passwordEncoder.encode("ModeratorPassword"), "ModeratorPhoneNumber");
        moderatorUser.setRoles(List.of(moderatorRole));

        userRepository.save(moderatorUser);
    }

    private void initUser() {
        var userRole = roleRepository.findByName(UserRoles.USER).orElseThrow();

        var user = new User("User1FirstName", "User1LastName", "User1Name", "User1Email",
                passwordEncoder.encode("UserPassword"), "User1PhoneNumber");
        user.setRoles(List.of(userRole));

        userRepository.save(user);
    }

    private void addCategory() throws IOException {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Hair");
        this.categoryService.addCategory(categoryDTO);
    }

    private void addService() throws IOException {
        ServiceProductRequestDTO service = new ServiceProductRequestDTO();
        service.setTitle("Стрижка");
        service.setDescription("Стрижка волос и моделирование бороды");
        service.setCategoryId(UUID.fromString("f15f8176-8ea3-4de4-b77c-ee73101d6ae4"));
        Long price = (long) 100;
        service.setPrice(price);
        service.setCreatedById(UUID.fromString("e0925dac-4266-4d62-8000-95fd39b1a156"));
        this.serviceProductService.addServiceProduct(service);
    }
}
