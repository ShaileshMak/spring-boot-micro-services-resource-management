package com.shailesh.mak.employeedashboardmicroservice.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.shailesh.mak.employeedashboardmicroservice.models.Employee;
import com.shailesh.mak.employeedashboardmicroservice.models.EmployeeResponse;
import com.shailesh.mak.employeedashboardmicroservice.models.Project;
import com.shailesh.mak.employeedashboardmicroservice.models.ProjectEmployee;
import com.shailesh.mak.employeedashboardmicroservice.models.ProjectsResponse;

@Service
public class EmployeeDashboardService {

	@Autowired
	private RestTemplate restTemplate;

	public EmployeeResponse setEmployee(final Employee employee) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		final HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
		final ResponseEntity<Employee> employeeResponse = restTemplate
				.postForEntity("http://localhost:8082/api/employees/employee", entity, Employee.class);

		final Employee newEmployee = employeeResponse.getBody();
		final Project newProject;
		EmployeeResponse employeeResponseObj = setEmployeeResponseObject(newEmployee);
		if (employee.getProjectId() != null) {
			final Map<String, String> map = new HashMap<>();
			map.put("firstName", employee.getFirstName());
			map.put("lastName", employee.getLastName());
			map.put("employeeId", employee.getEmployeeId());

			final ResponseEntity<Project> projectResponse = restTemplate.postForEntity(
					"http://localhost:8083/api/projects/" + employee.getProjectId() + "/employee", map, Project.class);
			newProject = projectResponse.getBody();
			newEmployee.setProject(newProject);
			employeeResponseObj = setEmployeeResponseObject(newEmployee, newProject);
		}
		return employeeResponseObj;
	}

	private EmployeeResponse setEmployeeResponseObject(Employee employee, Project project) {
		return new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
				employee.getDateOfBirth(), employee.getSalary(), employee.getAge(), employee.getLob(),
				employee.getDepartment(), project);
	}

	private EmployeeResponse setEmployeeResponseObject(Employee employee) {
		return new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
				employee.getDateOfBirth(), employee.getSalary(), employee.getAge(), employee.getLob(),
				employee.getDepartment());
	}

	public Employee getEmployeeItem(final String employeeId) {

		final Employee employee = restTemplate
				.getForObject("http://localhost:8082/api/employees/employee/" + employeeId, Employee.class);

		final Project project = restTemplate.getForObject("http://localhost:8083e/api/projects/employee/" + employeeId,
				Project.class);

		employee.setProject(project);

		return employee;
	}

	public ProjectsResponse getAllProjects() {
		final ResponseEntity<ProjectsResponse> responseEntity = restTemplate
				.getForEntity("http://localhost:8083/api/projects", ProjectsResponse.class);
		final ProjectsResponse projects = responseEntity.getBody();
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return projects;
		} else {
			System.out.println("Request Failed");
			return null;
		}
	}

	public Project assignProjectToEmployee(String projectId, String employeeId, ProjectEmployee employee) {
		final MultiValueMap<String, String> employeeMap = new LinkedMultiValueMap<>();
		employeeMap.add("employeeId", employeeId);
		employeeMap.add("projectId", projectId);

		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		final HttpEntity<MultiValueMap<String, String>> employeeEntity = new HttpEntity<MultiValueMap<String, String>>(
				employeeMap, headers);

		final ResponseEntity<Employee> updatedEmployee = restTemplate.exchange(
				"http://localhost:8082/api/employees/" + projectId + "/" + employeeId, HttpMethod.PUT, employeeEntity,
				Employee.class);

		System.out.println(updatedEmployee.getBody().toString());

		final Project newProject;
		final Map<String, String> map = new HashMap<>();
		map.put("firstName", employee.getFirstName());
		map.put("lastName", employee.getLastName());
		map.put("employeeId", employee.getEmployeeId());

		final ResponseEntity<Project> projectResponse = restTemplate.postForEntity(
				"http://localhost:8083/api/projects/" + projectId + "/employee", map, Project.class);
		newProject = projectResponse.getBody();
		System.out.println(newProject.toString());
		return newProject;
	}
}
