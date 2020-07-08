package com.shailesh.mak.employeedashboardmicroservice.models;

import java.util.List;

public class EmployeesResponse {
	private List<EmployeeResponse> employees;

	public EmployeesResponse() {
	}

	public EmployeesResponse(List<EmployeeResponse> employees) {
		super();
		this.employees = employees;
	}

	public List<EmployeeResponse> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeResponse> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "EmployeesResponse [employees=" + employees + "]";
	}
}
