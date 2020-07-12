package com.shailesh.mak.projectservice.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shailesh.mak.projectservice.models.Employee;
import com.shailesh.mak.projectservice.models.Project;
import com.shailesh.mak.projectservice.models.Projects;
import com.shailesh.mak.projectservice.services.ProjectService;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@PostMapping("/projects/project")
	@CrossOrigin
	@JsonIgnoreProperties
	public ResponseEntity<Project> addProject(@RequestBody @Valid final Project project) {
		return projectService.addProject(project);
	}

	@PostMapping("/projects/{projectId}/employee")
	public ResponseEntity<Project> addEmployee(@PathVariable final Long projectId,
			@Valid @RequestBody final Employee employee) {
		try {
			return projectService.addEmployee(projectId, employee);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@PutMapping("/projects/manager/{projectId}/{employeeId}")
	public ResponseEntity<Project> addManagerToProject(@PathVariable final Long projectId,
													   @PathVariable final String employeeId) {
		try {
			return projectService.assignManagerToProject(projectId, employeeId);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@GetMapping("/projects")
	public ResponseEntity<Projects> getProjects() {
		return projectService.getProjects();
	}

	@GetMapping("/projects/{id}")
	public ResponseEntity<Project> getProject(@PathVariable final Long id) {
		return projectService.getProjectById(id);
	}

	@GetMapping("/projects/employee/{employeeId}")
	public ResponseEntity<Project> getProjectsByEmployeeId(@PathVariable final Long employeeId) {
		return projectService.getProjectsByEmployeeId(employeeId);
	}

}
