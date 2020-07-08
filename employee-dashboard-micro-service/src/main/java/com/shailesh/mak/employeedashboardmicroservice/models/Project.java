package com.shailesh.mak.employeedashboardmicroservice.models;

import java.time.LocalDateTime;
import java.util.Set;

public class Project {

	private Long id;
	private String projectName;
	private String projectDes;
	private String projectManager;
	private Set<Employee> projectMembers;
	private LocalDateTime projectCreationDate;
	private String projectStatus;

	public Project() {
	}

	public Project(final Long id, final String projectName, final String projectDes, final String projectManager,
			final Set<Employee> projectMembers, final LocalDateTime projectCreationDate, final String projectStatus) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.projectDes = projectDes;
		this.projectManager = projectManager;
		this.projectMembers = projectMembers;
		this.projectCreationDate = projectCreationDate;
		this.projectStatus = projectStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDes() {
		return projectDes;
	}

	public void setProjectDes(final String projectDes) {
		this.projectDes = projectDes;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(final String projectManager) {
		this.projectManager = projectManager;
	}

	public Set<Employee> getProjectMemebers() {
		return projectMembers;
	}

	public void setProjectMemebers(final Set<Employee> projectMembers) {
		this.projectMembers = projectMembers;
	}

	public LocalDateTime getProjectCreationDate() {
		return projectCreationDate;
	}

	public void setProjectCreationDate(final LocalDateTime projectCreationDate) {
		this.projectCreationDate = projectCreationDate;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(final String projectStatus) {
		this.projectStatus = projectStatus;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", projectDes=" + projectDes + ", projectManager="
				+ projectManager + ", projectMembers=" + projectMembers + ", projectCreationDate=" + projectCreationDate
				+ ", projectStatus=" + projectStatus + "]";
	}
}
