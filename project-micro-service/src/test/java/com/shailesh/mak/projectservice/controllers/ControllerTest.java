package com.shailesh.mak.projectservice.controllers;

import com.shailesh.mak.projectservice.models.Employee;
import com.shailesh.mak.projectservice.models.Employees;
import com.shailesh.mak.projectservice.models.Project;
import com.shailesh.mak.projectservice.models.Projects;
import com.shailesh.mak.projectservice.services.EmployeeService;
import com.shailesh.mak.projectservice.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ControllerTest {

    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private ProjectController projectController;

    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    private Employee employee = new Employee("Sam", "Mak", "1");
    private Set<Employee> employeeList = new HashSet();
    private Project project = new Project("project", "description", "1", employeeList, "WIP");

    @Test
    public void setEmployeeTest() throws Exception {
        ResponseEntity<Employee> employeeResponseEntity = new ResponseEntity<>(employee, HttpStatus.OK);
        when(employeeService.setEmployee(any(Employee.class))).thenReturn(employeeResponseEntity);
        String requestBody = "{\"firstName\":\"Sam\",\"lastName\":\"Mak\",\"employeeId\":\"1\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeesTest() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        Employees employees = new Employees(employeeList);
        ResponseEntity<Employees> employeesResponseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
        when(employeeService.getEmployees()).thenReturn(employeesResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].firstName").value("Sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].lastName").value("Mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeTest() throws Exception {
        ResponseEntity<Employee> employeeResponseEntity = new ResponseEntity<>(employee, HttpStatus.OK);
        when(employeeService.getEmployee(anyString())).thenReturn(employeeResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void addProjectTest() throws Exception {
        String request = "{\"projectName\":\"project\",\"projectDes\":\"description\",\"projectManager\":\"1\",\"projectStatus\":\"WIP\"}";
        ResponseEntity<Project> projectResponseEntity = new ResponseEntity<>(project, HttpStatus.OK);
        when(projectService.addProject(any(Project.class))).thenReturn(projectResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectStatus").value("WIP"))
                .andExpect(status().isOk());
    }

    @Test
    public void addEmployeeTest() throws Exception {
        employeeList.add(employee);
        project.setProjectMemebers(employeeList);
        String request = "{\"firstName\":\"Sam\",\"lastName\":\"Mak\",\"employeeId\":\"1\"}";
        ResponseEntity<Project> projectResponseEntity = new ResponseEntity<>(project, HttpStatus.OK);
        when(projectService.addEmployee(any(Long.class), any(Employee.class))).thenReturn(projectResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectStatus").value("WIP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers[*].firstName").value("Sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers[*].lastName").value("Mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers[*].employeeId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void addManagerToProjectTest() throws Exception {
        employeeList.add(employee);
        project.setProjectMemebers(employeeList);
        ResponseEntity<Project> projectResponseEntity = new ResponseEntity<>(project, HttpStatus.OK);
        when(projectService.assignManagerToProject(any(Long.class), anyString())).thenReturn(projectResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/projects/manager/1/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectStatus").value("WIP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers[*].firstName").value("Sam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers[*].lastName").value("Mak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectMemebers[*].employeeId").value("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectsTest() throws Exception {
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        Projects projects = new Projects(projectList);
        ResponseEntity<Projects> projectsEntity = new ResponseEntity<>(projects, HttpStatus.OK);
        when(projectService.getProjects()).thenReturn(projectsEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*]projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*]projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*]projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projects[*]projectStatus").value("WIP"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectTest() throws Exception {
        ResponseEntity<Project> projectResponseEntity = new ResponseEntity<>(project, HttpStatus.OK);
        when(projectService.getProjectById(any(Long.class))).thenReturn(projectResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectStatus").value("WIP"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectsByEmployeeIdTest() throws Exception {
        ResponseEntity<Project> projectResponseEntity = new ResponseEntity<>(project, HttpStatus.OK);
        when(projectService.getProjectsByEmployeeId(any(Long.class))).thenReturn(projectResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/employee/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("project"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectDes").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectManager").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectStatus").value("WIP"))
                .andExpect(status().isOk());
    }
}