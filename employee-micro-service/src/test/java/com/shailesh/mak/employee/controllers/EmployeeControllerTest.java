package com.shailesh.mak.employee.controllers;

import com.datastax.driver.core.utils.UUIDs;
import com.shailesh.mak.employee.models.Employee;
import com.shailesh.mak.employee.models.Employees;
import com.shailesh.mak.employee.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private UUID id = UUIDs.timeBased();
    private Employee employee = new Employee(id,
            "1",
            "Sam",
            "Mak",
            LocalDate.of(1980, 03, 12),
            120000.0,
            43,
            "CCB",
            "Development",
            "1");

    @Test
    public void setEdmployeeTest() throws Exception {
        ResponseEntity<Employee> responseEmployee = new ResponseEntity<>(employee, HttpStatus.CREATED);
        when(employeeService.setEmployee(any(Employee.class))).thenReturn(responseEmployee);
        String request = "{\"employeeId\":\"3\",\"firstName\":\"Raj\",\"lastName\":\"Mallu\",\"dateOfBirth\":\"1977-06-25\",\"salary\":\"120000\",\"age\":\"44\",\"lob\":\"GTA\",\"department\":\"localization\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/employee")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("120000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("43"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId").value("1"))
                .andExpect(status().isCreated());
    }

    @Test
    public void geEmployeesTest() throws Exception{
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        Employees employees = new Employees(employeeList);
        ResponseEntity<Employees> responseEmployee = new ResponseEntity<>(employees, HttpStatus.OK);
        when(employeeService.geEmployees()).thenReturn(responseEmployee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].firstName").value("sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].lastName").value("mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].salary").value(120000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].age").value(43))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].department").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].projectId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeIdTest() throws Exception{
        ResponseEntity<Employee> responseEmployee = new ResponseEntity<>(employee, HttpStatus.OK);
        when(employeeService.getEmployeeById(any())).thenReturn(responseEmployee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/" + id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("120000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("43"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByNameTest() throws Exception{
        ResponseEntity<Employee> responseEmployee = new ResponseEntity<>(employee, HttpStatus.OK);
        when(employeeService.getEmployeeByName(anyString(), anyString())).thenReturn(responseEmployee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/sam/mak" + id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("120000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("43"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByEmployeeIdTest() throws Exception{
        ResponseEntity<Employee> responseEmployee = new ResponseEntity<>(employee, HttpStatus.OK);
        when(employeeService.getEmployeeByEmployeeId(anyString())).thenReturn(responseEmployee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/employee/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("120000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("43"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployeeProjectTest() throws Exception{
        ResponseEntity<Employee> responseEmployee = new ResponseEntity<>(employee, HttpStatus.OK);
        when(employeeService.updateProjectIdOfEmployee(anyString(), anyString())).thenReturn(responseEmployee);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/1/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("120000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("43"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectId").value("1"))
                .andExpect(status().isOk());
    }
}