package com.web.time_to_book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.web.time_to_book.dtos.RoleDTO;
import com.web.time_to_book.dtos.request.UserRequestDTO;
import com.web.time_to_book.services.RoleService;
import com.web.time_to_book.services.UserService;

@Component
public class CommandRunner implements CommandLineRunner {

    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final RoleService roleService;
    private final UserService userService;

    public CommandRunner(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start!");

        initializeRoles();
        addUser();
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
}
