package com.shailesh.mak.projectservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shailesh.mak.projectservice.models.Employee;
import com.shailesh.mak.projectservice.models.Employees;
import com.shailesh.mak.projectservice.services.EmployeeService;

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
		return employeeService.getEmployees();
	}

	@GetMapping("/employees/{employeeId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable final String employeeId) {
		return employeeService.getEmployee(employeeId);
	}
}
