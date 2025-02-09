package com.employee.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class EmployeeDTO {
    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @Value("dev")
    private String position;

    @Min(value = 1 , message =  "Salary can not be less that 1.")
    private int salary;

    @Email(message = "Email should be valid.")
    @NonNull
    private String email;

    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
            message = "Invalid phone number format."
    )
    @NonNull
    private String phoneNumber;
}
