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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.error("Employee id: " + Id + " not found");
            throw new EmployeeNotFoundException("Employee id: " + Id + " not found");
        }
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO) {
        validateNotNullEmployeeDTO(employeeDTO);
        Employee employee = new Employee(
                employeeDTO.getFirstName()
                ,employeeDTO.getLastName()
                ,employeeDTO.getPosition()
                , employeeDTO.getSalary()
                ,employeeDTO.getEmail()
                ,employeeDTO.getPhoneNumber());
        validateEmployeeEGPhoneNumber(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("The Employee " + savedEmployee + " is saved successfully");
        return savedEmployee;
    }

    @Override
    public void deleteById(long Id) {
        if(employeeRepository.findById(Id).isPresent()){
            employeeRepository.deleteById(Id);
            log.info("The Employee id: " + Id + " is deleted successfully");
        }else{
            log.error("Employee id: " + Id + " not found");
            throw new EmployeeNotFoundException("Employee id: " + Id + " not found");
        }
    }

    private void validateNotNullEmployeeDTO(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            log.error("The EmployeeDTO object have a null value.");
            throw new NullPointerException("Employee object cannot be null");
        }
    }

    public void validateEmployeeEGPhoneNumber(Employee employee) {
        if (!Validation.isValidEGPhoneNumber(employee.getPhoneNumber())) {
            log.error("The Employee {}, have Invalid EG phone number format.", employee);
            throw new IllegalArgumentException("Invalid EG phone number format");
        }
    }
}
