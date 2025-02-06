package com.employee.demo.service;

import com.employee.demo.controller.exception.EmployeeNotFoundException;
import com.employee.demo.dao.EmployeeRepository;
import com.employee.demo.dto.EmployeeDTO;
import com.employee.demo.entity.Employee;
import com.employee.demo.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public Employee findById(long Id) {
        if(employeeRepository.findById(Id).isPresent()){
            return employeeRepository.findById(Id).get();
        }else{
            throw new EmployeeNotFoundException("Employee id: " + Id + " not found");
        }
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        validateNotNullEmployeeFields(employeeDTO);
        Employee employee = new Employee(employeeDTO.getFirstName(),employeeDTO.getLastName(),employeeDTO.getPosition(), employeeDTO.getSalary(),employeeDTO.getEmail(),employeeDTO.getPhoneNumber());
        validateEmployeeEGPhoneNumber(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(long Id) {
        if(employeeRepository.findById(Id).isPresent()){
            employeeRepository.deleteById(Id);
        }else{
            throw new EmployeeNotFoundException("Employee id: " + Id + " not found");
        }
    }

    private void validateNotNullEmployeeFields(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            throw new IllegalArgumentException("Employee object cannot be null");
        }
    }

    public void validateEmployeeEGPhoneNumber(Employee employee) {
        if (!Validation.isValidEGPhoneNumber(employee.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid EG phone number format");
        }
    }
}
