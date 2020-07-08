package com.shailesh.mak.employee.models;

import java.util.List;

public class Employees {
	private List<Employee> employees;

	public Employees() {}
	
	public Employees(final List<Employee> employees) {
		super();
		this.employees = employees;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(final List<Employee> employees) {
		this.employees = employees;
	}
	
}
