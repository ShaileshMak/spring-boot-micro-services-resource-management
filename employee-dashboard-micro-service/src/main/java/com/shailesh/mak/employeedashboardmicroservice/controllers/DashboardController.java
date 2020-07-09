package com.shailesh.mak.employeedashboardmicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shailesh.mak.employeedashboardmicroservice.models.Employee;
import com.shailesh.mak.employeedashboardmicroservice.models.EmployeeResponse;
import com.shailesh.mak.employeedashboardmicroservice.models.Project;
import com.shailesh.mak.employeedashboardmicroservice.models.ProjectEmployee;
import com.shailesh.mak.employeedashboardmicroservice.models.ProjectsResponse;
import com.shailesh.mak.employeedashboardmicroservice.services.EmployeeDashboardService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DashboardController {

	@Autowired
	EmployeeDashboardService employeeDashboardService;

	@PostMapping("/employees/employee")
	public EmployeeResponse setEmployee(@RequestBody @DateTimeFormat(pattern = "dd.MM.yyyy") final Employee employee) {
		return employeeDashboardService.setEmployee(employee);
	}

	@PutMapping("/projects/{projectId}/{employeeId}")
	public Project assignProjectToEmployee(@PathVariable final String projectId,
			@PathVariable final String employeeId,
			@RequestBody final ProjectEmployee employee) {
		return employeeDashboardService.assignProjectToEmployee(projectId, employeeId, employee);
	}

	@PutMapping("/projects/manager/{projectId}/{employeeId}")
	public Project assignManagerToProject(@PathVariable final String projectId,
										   @PathVariable final String employeeId) {
		return employeeDashboardService.assignManagerToProject(projectId, employeeId);
	}

	@GetMapping("/employees/employee/{employeeId}")
	public Employee getEmployee(@PathVariable final String employeeId) {
		return employeeDashboardService.getEmployeeItem(employeeId);
	}

	@GetMapping("/projects")
	public ProjectsResponse getAllProjects() {
		return employeeDashboardService.getAllProjects();
	}
}
