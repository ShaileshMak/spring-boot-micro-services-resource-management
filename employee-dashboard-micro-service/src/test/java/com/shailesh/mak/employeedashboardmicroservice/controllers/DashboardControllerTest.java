package com.shailesh.mak.employeedashboardmicroservice.controllers;

import com.shailesh.mak.employeedashboardmicroservice.models.*;
import com.shailesh.mak.employeedashboardmicroservice.services.EmployeeDashboardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class DashboardControllerTest {
    @Autowired
    private DashboardController dashboardController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeDashboardService employeeDashboardService;

    private Set<Employee> employeeSet = new HashSet<Employee>();
    private Project project = new Project (
            1L,
            "project",
            "description",
            "1",
            employeeSet,
            LocalDateTime.now(),
            "WIP"
    );
    private EmployeeResponse employeeResponse = new EmployeeResponse(
            "1",
            "Sam",
            "Mak",
            LocalDate.of(1980,3,12),
            120000.0,
            38,
            "CCB",
            "Development",
            project
            );
    private Employee employee = new Employee(
            "1",
            project,
            "Sam",
            "Mak",
            LocalDate.of(1980,3,12),
            120000.0,
            38,
            "CCB",
            "Development",
            "1"
    );

    @Test
    public void setEmployeeTest() throws Exception {
        when(employeeDashboardService.setEmployee(any(Employee.class))).thenReturn(employeeResponse);
        String request = "{\"employeeId\":\"4\",\"firstName\":\"Raj\",\"lastName\":\"Mallu\",\"dateOfBirth\":\"1977-06-25\",\"salary\":\"120000\",\"age\":\"44\",\"lob\":\"GTA\",\"department\":\"localization\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("120000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("38"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Development"))
                .andExpect(status().isOk());
    }

    @Test
    public void assignProjectToEmployeeTest() throws Exception {
        when(employeeDashboardService.assignProjectToEmployee(anyString(), anyString(), any(ProjectEmployee.class))).thenReturn(project);
        String request = "{\"firstName\":\"Raj\",\"lastName\":\"Mallu\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/1/0a61df70-c18c-11ea-bdc5-116244b8fc00")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectStatus").value("WIP"))
                .andExpect(status().isOk());
    }

    @Test
    public void assignManagerToProjectTest() throws Exception {
        when(employeeDashboardService.assignManagerToProject(anyString(), anyString())).thenReturn(project);
        String request = "{\"firstName\":\"Raj\",\"lastName\":\"Mallu\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/manager/1/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectStatus").value("WIP"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployee() throws Exception {
        when(employeeDashboardService.getEmployeeItem(anyString())).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/employee/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value("1980-03-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("120000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("38"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lob").value("CCB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value("Development"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllProjects() throws Exception {
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        ProjectsResponse projectsResponse = new ProjectsResponse(projectList);
        when(employeeDashboardService.getAllProjects()).thenReturn(projectsResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*].projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*].projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*].projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*].projectStatus").value("WIP"))
                .andExpect(status().isOk());
    }
}