package com.shailesh.mak.employeedashboardmicroservice.models;

import java.time.LocalDate;

public class EmployeeResponse {

	private String employeeId;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private Double salary;
	private Integer age;
	private String lob;
	private String department;
	private Project project;

	public EmployeeResponse() {
	}

	public EmployeeResponse(String employeeId, String firstName, String lastName,
			LocalDate dateOfBirth, Double salary, Integer age, String lob, String department, Project project) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
		this.age = age;
		this.lob = lob;
		this.department = department;
		this.project = project;
	}
	
	public EmployeeResponse(String employeeId, String firstName, String lastName,
			LocalDate dateOfBirth, Double salary, Integer age, String lob, String department) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
		this.age = age;
		this.lob = lob;
		this.department = department;
	}


	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "EmployeeResponse [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", salary=" + salary + ", age=" + age + ", lob=" + lob + ", department=" + department + ", Project=" + project.toString() + "]";
	}


}
