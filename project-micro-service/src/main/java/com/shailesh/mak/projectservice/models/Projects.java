package com.shailesh.mak.projectservice.models;

import java.util.List;

public class Projects {
	private List<Project> projects;

	public Projects() {
	}

	public Projects(final List<Project> projects) {
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
