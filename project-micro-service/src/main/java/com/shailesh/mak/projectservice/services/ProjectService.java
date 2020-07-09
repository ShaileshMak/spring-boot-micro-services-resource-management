package com.shailesh.mak.projectservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shailesh.mak.projectservice.models.Employee;
import com.shailesh.mak.projectservice.models.Project;
import com.shailesh.mak.projectservice.models.Projects;
import com.shailesh.mak.projectservice.repositories.EmployeeRepository;
import com.shailesh.mak.projectservice.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	public ResponseEntity<Project> addProject(@Valid Project project) {
		final Optional<Project> _existingProject = projectRepository.findByProjectName(project.getProjectName());
		if (_existingProject.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		try {
			final Project _project = new Project(project.getProjectName(), project.getProjectDes(), project.getProjectManager(),
					project.getProjectMemebers(), project.getProjectStatus());
			projectRepository.save(_project);
			return new ResponseEntity<Project>(_project, HttpStatus.OK);

		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Project> addEmployee(Long projectId, @Valid Employee employee) throws Exception{
		final Optional<Employee> _existingEmployee = employeeRepository
				.findByFirstNameAndLastName(employee.getFirstName(), employee.getLastName());
		if (_existingEmployee.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return projectRepository.findById(projectId).map(project -> {
			employee.setProject(project);
			employeeRepository.save(employee);
			return new ResponseEntity<Project>(project, HttpStatus.OK);
		}).orElseThrow(() -> new Exception("Project not found!"));
	}

	public ResponseEntity<Projects> getProjects() {
		try {
			final List<Project> _projects = new ArrayList<Project>();
			projectRepository.findAll().forEach(_projects::add);
			
			final Projects projects = new Projects(_projects);

			return new ResponseEntity<>(projects, HttpStatus.OK);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Project> getProjectById(Long id) {
		try {
			final Optional<Project> _project = projectRepository.findById(id);
			if (_project.isPresent()) {
				return new ResponseEntity<>(_project.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Project> getProjectsByEmployeeId(Long employeeId) {
		try {
			if (!employeeRepository.existsById(employeeId)) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			final Optional<Employee> _employee = employeeRepository.findByEmployeeId(String.valueOf(employeeId));
			if (_employee.isPresent()) {
				return new ResponseEntity<>(_employee.get().getProject(), HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Project> assignManagerToProject(Long projectId, String employeeId) {
		try {
			final Optional<Project> _existingProject = projectRepository.findById(projectId);
			if (_existingProject.isPresent()) {
				Project _project = _existingProject.get();
				_project.setProjectManager(employeeId.toString());
				projectRepository.save(_project);
				return new ResponseEntity<>(_project, HttpStatus.OK);
			}
		} catch (final Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
