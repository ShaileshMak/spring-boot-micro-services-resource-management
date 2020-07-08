package com.shailesh.mak.employeedashboardmicroservice.models;

import java.util.List;

public class ProjectsResponse {
	private List<Project> projects;

	public ProjectsResponse() {
	}

	public ProjectsResponse(final List<Project> projects) {
		super();
		this.projects = projects;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(final List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Projects [projects=" + projects + "]";
	}

}
