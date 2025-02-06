package com.employee.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class EmployeeDTO {
    @Value("Default First Name")
    private String firstName;

    @Value("Default Last Name")
    private String lastName;

    @Value("dev")
    private String position;

    @Min(value = 1 , message =  "Salary can not be less that 1.")
    private int salary;

    @Email(message = "Email should be valid.")
    @NonNull
    private String email;

    @NonNull
    private String phoneNumber;
}
