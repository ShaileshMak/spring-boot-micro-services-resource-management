package com.shailesh.mak.projectservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shailesh.mak.projectservice.models.Employee;
import com.shailesh.mak.projectservice.models.Employees;
import com.shailesh.mak.projectservice.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public ResponseEntity<Employee> setEmployee(Employee employee) {
		try {
			final Optional<Employee> _existingEmployee = employeeRepository.findByEmployeeId(employee.getEmployeeId());
			if (_existingEmployee.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}

			final Employee _employee = new Employee(employee.getFirstName(), employee.getLastName(),
					employee.getEmployeeId());
			employeeRepository.save(_employee);
			return new ResponseEntity<Employee>(_employee, HttpStatus.CREATED);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Employees> getEmployees() {
		try {
			final List<Employee> _employees = new ArrayList<Employee>();
			employeeRepository.findAll().forEach(_employees::add);
			if (_employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			final Employees employees = new Employees(_employees);

			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Employee> getEmployee(String employeeId) {
		try {
			final Optional<Employee> _employee = employeeRepository.findByEmployeeId(employeeId);
			if (_employee.isPresent()) {
				return new ResponseEntity<>(_employee.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
