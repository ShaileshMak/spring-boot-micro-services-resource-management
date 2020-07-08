package com.shailesh.mak.employee.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.utils.UUIDs;
import com.shailesh.mak.employee.models.Employee;
import com.shailesh.mak.employee.models.Employees;
import com.shailesh.mak.employee.repositories.EmployeeRepository;

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

			final Employee _employee = new Employee(UUIDs.timeBased(), employee.getEmployeeId(),
					employee.getFirstName(), employee.getLastName(), employee.getDateOfBirth(), employee.getSalary(),
					employee.getAge(), employee.getLob(), employee.getDepartment(), employee.getProjectId());
			employeeRepository.save(_employee);
			return new ResponseEntity<Employee>(_employee, HttpStatus.CREATED);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<Employees> geEmployees() {
		try {
			final List<Employee> _employees = new ArrayList<Employee>();
			employeeRepository.findAll().forEach(_employees::add);

			final Employees employees = new Employees(_employees);

			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Employee> getEmployeeById(UUID id) {
		try {
			final Optional<Employee> _employee = employeeRepository.findById(id);
			if (_employee.isPresent()) {
				return new ResponseEntity<>(_employee.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Employee> getEmployeeByName(String firstName, String lastName) {
		try {
			final Optional<Employee> _employee = employeeRepository.findByFirstNameAndLastName(firstName, lastName);
			if (_employee.isPresent()) {
				return new ResponseEntity<>(_employee.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Employee> getEmployeeByEmployeeId(String employeeId) {
		try {
			final Optional<Employee> _employee = employeeRepository.findByEmployeeId(employeeId);
			if (_employee.isPresent()) {
				return new ResponseEntity<>(_employee.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Employee> updateProjectIdOfEmployee(String projectId, String employeeId) {
		try {
			Optional<Employee> employeeData = employeeRepository.findById(UUID.fromString(employeeId));

			if (employeeData.isPresent()) {
				Employee _employee = employeeData.get();
				_employee.setProjectId(projectId);
				return new ResponseEntity<>(employeeRepository.save(_employee), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
