package com.employee.demo.service;

import com.employee.demo.controller.exception.EmployeeNotFoundException;
import com.employee.demo.dao.EmployeeRepository;
import com.employee.demo.dto.EmployeeDTO;
import com.employee.demo.entity.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Employee save(@NotNull @Valid EmployeeDTO employeeDTO) {
        Employee employee =  Employee.builder().
                firstName(employeeDTO.getFirstName()).
                lastName(employeeDTO.getLastName()).
                email(employeeDTO.getEmail()).
                phoneNumber(employeeDTO.getPhoneNumber()).
                position(employeeDTO.getPosition()).
                        salary(employeeDTO.getSalary()).
                build();
        Employee savedEmployee = employeeRepository.save(employee);
        log.debug("The Employee " + savedEmployee + " is saved successfully");
        return savedEmployee;
    }

    @Transactional
    @Override
    public void deleteById(long Id) {
        if(employeeRepository.findById(Id).isPresent()){
            employeeRepository.deleteById(Id);
            log.debug("The Employee id: " + Id + " is deleted successfully");
        }else{
            log.error("Employee id: " + Id + " not found");
            throw new EmployeeNotFoundException("Employee id: " + Id + " not found");
        }
    }
}
