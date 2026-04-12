package com.HaritMitraBack.mitra.dto;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.*;

public class SignupRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 20, message = "Name must be 3-20 characters")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&+=]{6,}$",
            message = "Password must be at least 6 chars, include letters & numbers"
    )
    private String password;




    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}