package com.shailesh.mak.employee.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shailesh.mak.employee.models.Employee;
import com.shailesh.mak.employee.models.Employees;
import com.shailesh.mak.employee.services.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employees/employee")
	public ResponseEntity<Employee> setEdmployee(
			@RequestBody @DateTimeFormat(pattern = "dd.MM.yyyy") final Employee employee) {
		return employeeService.setEmployee(employee);
	}

	@GetMapping("/employees")
	public ResponseEntity<Employees> getEmployees() {
		return employeeService.geEmployees();
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable final UUID id) {
		return employeeService.getEmployeeById(id);
	}

	@GetMapping("/employees/{firstName}/{lastName}")
	public ResponseEntity<Employee> getEmployeeByName(@PathVariable final String firstName,
			@PathVariable final String lastName) {
		return employeeService.getEmployeeByName(firstName, lastName);
	}

	@GetMapping("/employees/employee/{employeeId}")
	public ResponseEntity<Employee> getEmployeeByEmployeeId(@PathVariable final String employeeId) {
		return employeeService.getEmployeeByEmployeeId(employeeId);
	}

	@PutMapping("employees/{projectId}/{employeeId}")
	public ResponseEntity<Employee> updateEmployeeProject(@PathVariable final String projectId,
			@PathVariable final String employeeId) {
		return employeeService.updateProjectIdOfEmployee(projectId, employeeId);
	}
}
