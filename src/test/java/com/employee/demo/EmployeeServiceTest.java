package com.employee.demo;

import com.employee.demo.controller.exception.EmployeeNotFoundException;
import com.employee.demo.dao.EmployeeRepository;
import com.employee.demo.dto.EmployeeDTO;
import com.employee.demo.entity.Employee;
import com.employee.demo.service.EmployeeServiceImpl;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_ReturnsPaginatedEmployees() {
        int pageNumber = 0;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Employee> employeeList = getEmployeeList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), employeeList.size());
        List<Employee> paginatedList = employeeList.subList(start, end);
        Page<Employee> employeePage = new PageImpl<>(paginatedList, pageable, employeeList.size());
        // When
        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);
        // Then
        List<Employee> result = employeeService.findAll(pageable);
        assertNotNull(result);
        assertEquals(paginatedList.size(), result.size());
        assertEquals(paginatedList.getFirst().getFirstName(), result.getFirst().getFirstName());
    }

    @Test
    void testFindByID_ReturnsEmployee() {
        long ID = 1;
        Employee employee = getEmployee();
        // When
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
        // Then
        Employee result = employeeService.findById(ID);
        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
    }

    @Test
    void testFindByID_ReturnsIdNotExist() {
        long ID = 1;
        // When
        when(employeeRepository.findById(ID)).thenReturn(Optional.empty());
        // Then
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.findById(ID)
        );
    }

    @Test
    void testDeleteByID_ReturnsEmployee() {
        long ID = 1;
        Employee employee = getEmployee();
        // When
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
        // Then
        employeeService.deleteById(ID);
        assertAll(() -> employeeRepository.deleteById(ID));
    }

    @Test
    void testDeleteByID_ReturnsIdNotExist() {
        long ID = 1;
        // When
        when(employeeRepository.findById(ID)).thenReturn(Optional.empty());
        // Then
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.deleteById(ID)
        );
    }

    @Test
    void testSaveEmployeeDTO_ReturnsEmployee() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder().
                firstName("Omar").
                lastName("Awad").
                email("omar@gmail.com").
                phoneNumber("+201226776173").
                position("Java Dev").
                salary(7000).
                build();
        Employee employee = getEmployee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee testedEmployee = employeeService.save(employeeDTO);
        assertNotNull(testedEmployee);
        assertEquals("Omar", testedEmployee.getFirstName());
        assertEquals("Awad", testedEmployee.getLastName());
    }

    @Test
    void testSaveWithNullEmployeeDTO_ReturnsNullPointerException() {
        EmployeeDTO employeeDTO = null;
        assertThrows(NullPointerException.class,
                () -> employeeService.save(employeeDTO)
        );
    }

    @Test
    void testSaveWithInvalidPhoneNumber_ReturnsException() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName("Omar")
                .lastName("Awad")
                .email("omar@gmail.com")
                .phoneNumber("+2012267761s7") // here the phone number has a character.
                .position("Java Dev")
                .salary(7000)
                .build();
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        // Asserting that validation failed
        assertFalse(violations.isEmpty(), "Validation should fail due to invalid phone number");
    }

    @Test
    void testSaveWithInvalidEmail_ReturnsNotEmptyViolations() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName("Omar")
                .lastName("Awad")
                .email("omargmail.com") // Invalid email (missing @)
                .phoneNumber("+20122677617")
                .position("Java Dev")
                .salary(7000)
                .build();
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        // Asserting that validation failed
        assertFalse(violations.isEmpty(), "Validation should fail due to invalid email");
    }

    @Test
    void testNullFirstName_ReturnsException() {
        assertThrows(NullPointerException.class,
                () -> EmployeeDTO.builder()
                        .firstName(null)
                        .lastName("Awad")
                        .email("omar@gmail.com")
                        .phoneNumber("+20122677617")
                        .position("Java Dev")
                        .salary(7000)
                        .build()
        );
    }

    @Test
    void testNullLastName_ReturnsException() {
        assertThrows(NullPointerException.class,
                () -> EmployeeDTO.builder()
                        .firstName("Omar")
                        .lastName(null)
                        .email("omar@gmail.com")
                        .phoneNumber("+20122677617")
                        .position("Java Dev")
                        .salary(7000)
                        .build()
        );
    }

    @Test
    void testDefaultPosition_ReturnsDefaultEmployeePositions() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder().
                firstName("Omar").
                lastName("Awad").
                email("omar@gmail.com").
                phoneNumber("+201226776173").
                position(null). // here the position is initialized as null
                salary(700).
                build();
        Employee employee = Employee.builder().
                firstName("Omar").
                lastName("Awad").
                email("omar@gmail.com").
                phoneNumber("+201226776173").
                position("dev").
                salary(700).
                build();

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee testedEmployee = employeeService.save(employeeDTO);
        assertNotNull(testedEmployee);
        assertEquals("dev", testedEmployee.getPosition());
    }

    //dummy data of employees for testing
    private static List<Employee> getEmployeeList() {
        Employee employment1 = new Employee("Omar", "Awad", "Java Dev", 700, "omar@gmail.com", "+201226776173" );
        Employee employment2 = new Employee("Omar", "Awad", "Java Dev", 700, "omar@gmail.com", "+201226776173" );
        Employee employment3 = new Employee("Omar", "Awad", "Java Dev", 700, "omar@gmail.com", "+201226776173" );
        Employee employment4 = new Employee("Omar", "Awad", "Java Dev", 700, "omar@gmail.com", "+201226776173" );
        Employee employment5 = new Employee("Omar", "Awad", "Java Dev", 700, "omar@gmail.com", "+201226776173" );
        return Arrays.asList(employment1,employment2,employment3,employment4,employment5);
    }
    private static Employee getEmployee() {
        return new Employee("Omar", "Awad", "Java Dev", 700, "omar@gmail.com", "+201226776173" );
    }
}