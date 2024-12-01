package com.web.time_to_book.dtos.response;

public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String avatarURL;
    private Long numberOfAppointments;

    public UserResponseDTO(String firstName, String lastName, String username, String email, String password,
            String phoneNUmber, Long numberOfAppointments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNUmber;
        this.numberOfAppointments = numberOfAppointments;
    }

    protected UserResponseDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public Long getNumberOfAppointments() {
        return numberOfAppointments;
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

    public void setNumberOfAppointments(Long numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
