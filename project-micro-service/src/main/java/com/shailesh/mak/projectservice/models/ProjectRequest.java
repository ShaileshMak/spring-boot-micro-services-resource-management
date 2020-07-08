package com.shailesh.mak.projectservice.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProjectRequest {

	private String projectName;
	private String projectDes;
	private String projectManager;
	private ArrayList<String> projectMembers;
	private String projectStatus;

	public ProjectRequest() {
	}
	
	@JsonCreator
	public ProjectRequest(final String projectName, final String projectDes, final String projectManager,
			final ArrayList<String> projectMembers, final String projectStatus) {
		super();
		this.projectName = projectName;
		this.projectDes = projectDes;
		this.projectManager = projectManager;
		this.projectMembers = projectMembers;
		this.projectStatus = projectStatus;
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

	public ArrayList<String> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(final ArrayList<String> projectMembers) {
		this.projectMembers = projectMembers;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(final String projectStatus) {
		this.projectStatus = projectStatus;
	}

}