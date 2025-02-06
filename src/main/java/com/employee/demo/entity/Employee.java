package com.employee.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name="employee")
public class Employee {
    public Employee(String firstName, String lastName, String position, int salary, @NonNull String email, @NonNull String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private long employeeID;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="position")
    private String position;

    @Column(name="salary")
    private int salary;

    @NonNull
    @Column(name="email")
    private String email;

    @NonNull
    @Column(name="phone_number")
    private String phoneNumber;

}
