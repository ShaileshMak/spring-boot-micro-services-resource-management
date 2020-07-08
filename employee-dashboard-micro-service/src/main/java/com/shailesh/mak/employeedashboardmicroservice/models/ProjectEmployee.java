package com.shailesh.mak.employeedashboardmicroservice.models;

public class ProjectEmployee {
	private String firstName;
	private String lastName;
	private String employeeId;
	private String projectId;
	
	public ProjectEmployee() {
	}

	public ProjectEmployee(String firstName, String lastName, String employeeId, String projectId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeId = employeeId;
		this.projectId = projectId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "ProjectEmployee [firstName=" + firstName + ", lastName=" + lastName + ", employeeId=" + employeeId+ ", projectId=" + projectId
				+ "]";
	}

}
