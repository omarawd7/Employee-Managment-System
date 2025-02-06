package com.employee.demo.service;

import com.employee.demo.dto.EmployeeDTO;
import com.employee.demo.entity.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll(Pageable pageable);

    Employee findById(long theId);

    Employee save(EmployeeDTO employeeDTO);

    void deleteById(long theId);
}
