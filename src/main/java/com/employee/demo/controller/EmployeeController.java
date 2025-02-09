package com.employee.demo.controller;

import com.employee.demo.controller.exception.EmployeeNotFoundException;
import com.employee.demo.dto.EmployeeDTO;
import com.employee.demo.entity.Employee;
import com.employee.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //add new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.save(employeeDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //get all employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(Pageable pageable) {
        List<Employee> allEmployees = employeeService.findAll(pageable);
        if (allEmployees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    //get employee by ID
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee id: " + employeeId + "not found");
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    //get employee by ID
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee id: " + employeeId + "not found");
        }
        employeeService.deleteById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
