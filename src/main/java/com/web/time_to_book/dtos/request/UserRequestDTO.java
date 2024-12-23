package com.web.time_to_book.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {
    public String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatarURL;

    public UserRequestDTO(String firstName, String lastName, String username, String email, String phoneNUmber,
            String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNUmber;
        this.password = password;
    }

    public UserRequestDTO(String firstName, String lastName, String phoneNUmber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNUmber;
    }

    public UserRequestDTO() {
    }

    public String getId() {
        return id;
    }

    @NotBlank(message = "Имя обязательно")
    public String getFirstName() {
        return firstName;
    }

    @NotBlank(message = "Фамилия обязательна")
    public String getLastName() {
        return lastName;
    }

    @NotBlank(message = "Никнейм обязателен")
    public String getUsername() {
        return username;
    }

    @NotBlank(message = "Почта обязательна")
    public String getEmail() {
        return email;
    }

    @NotBlank(message = "Пароль обязателен")
    public String getPassword() {
        return password;
    }

    @NotBlank(message = "Номер телефона обязателен")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
