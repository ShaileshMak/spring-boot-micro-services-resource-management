package com.shailesh.mak.employee.models;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("employees")
public class Employee {

	@PrimaryKey
	private UUID id;

	@Column("employee_id")
	private String employeeId;

	@Column("first_name")
	private String firstName;

	@Column("last_name")
	private String lastName;

	@Column("date_of_birth")
	private LocalDate dateOfBirth;

	@Column("salary")
	private Double salary;

	@Column("age")
	private Integer age;

	@Column("lob")
	private String lob;

	@Column("department")
	private String department;

	@Column("project_id")
	private String projectId;

	public Employee() {
	}

	public Employee(final UUID id, final String employeeId, final String firstName, final String lastName,
			final LocalDate dateOfBirth, final Double salary, final Integer age, final String lob,
			final String department, final String projectId) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.firstName = firstName.toLowerCase();
		this.lastName = lastName.toLowerCase();
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
		this.age = age;
		this.lob = lob;
		this.department = department;
		this.projectId = projectId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(final String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName.toLowerCase();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName.toLowerCase();
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(final Double salary) {
		this.salary = salary;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(final String lob) {
		this.lob = lob;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(final String department) {
		this.department = department;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(final String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", employeeId=" + employeeId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", dateOfBirth=" + dateOfBirth + ", salary=" + salary + ", age=" + age + ", lob=" + lob
				+ ", department=" + department + ", projectId=" + projectId + "]";
	}

}
