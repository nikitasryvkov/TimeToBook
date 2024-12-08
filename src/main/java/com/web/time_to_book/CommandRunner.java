package com.web.time_to_book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.web.time_to_book.dtos.CategoryDTO;
import com.web.time_to_book.dtos.RoleDTO;
import com.web.time_to_book.dtos.request.ServiceProductRequestDTO;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.services.CategoryService;
import com.web.time_to_book.services.RoleService;
import com.web.time_to_book.services.ServiceProductService;
import com.web.time_to_book.services.UserService;

@Component
public class CommandRunner implements CommandLineRunner {

    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final RoleService roleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ServiceProductService serviceProductService;

    public CommandRunner(RoleService roleService, UserService userService, CategoryService categoryService,
            ServiceProductService serviceProductService) {
        this.roleService = roleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.serviceProductService = serviceProductService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start!");

        initializeRoles();
        // addService();
        // addUser();
        // addCategory();
    }

    private void initializeRoles() {
        if (roleService.findAllRoles().isEmpty()) {
            RoleDTO adminRole = new RoleDTO();
            RoleDTO userRole = new RoleDTO();
            adminRole.setName("ADMIN");
            userRole.setName("USER");
            roleService.addRole(adminRole);
            roleService.addRole(userRole);
        }
    }

    private void addUser() throws IOException {
        System.out.println("Enter user details in format: name;email;password");
        String[] userParams = this.bufferedReader.readLine().split(";");
        UserRequestDTO userDTO = new UserRequestDTO();
        userDTO.setFirstName(userParams[0]);
        userDTO.setLastName(userParams[1]);
        userDTO.setUsername(userParams[2]);
        userDTO.setEmail(userParams[3]);
        userDTO.setPassword(userParams[4]);
        userDTO.setPhoneNumber(userParams[5]);
        userDTO.setAvatarURL(userParams[6]);
        userDTO.setRoleName("USER");
        try {
            this.userService.addUser(userDTO);
            System.out.println("Successfully added user!");
        } catch (Exception e) {
            System.out.println("Error! Cannot add user!");
        }
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
        service.setCategoryId(UUID.fromString("97102e88-d216-4ef4-9af4-eb14ba63f7b4"));
        Long price = (long) 100;
        service.setPrice(price);
        service.setCreatedById(UUID.fromString("ee779985-b321-444e-9072-54642a9845ed"));
        this.serviceProductService.addServiceProduct(service);
    }
}
