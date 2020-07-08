package com.shailesh.mak.projectservice.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "projects")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Long id;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "project_des")
	private String projectDes;

	@Column(name = "project_manager")
	private String projectManager;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Column(name = "project_members")
	private Set<Employee> projectMembers;

	@JsonIgnore
	@Column(name = "project_creation_date")
	@CreationTimestamp
	private LocalDateTime projectCreationDate;

	@Column(name = "project_status")
	private String projectStatus;

	public Project() {
	}

	public Project(final String projectName, final String projectDes, final String projectManager,
			final Set<Employee> projectMembers, final String projectStatus) {
		super();
		this.projectName = projectName;
		this.projectDes = projectDes;
		this.projectManager = projectManager;
		this.projectMembers = projectMembers;
		this.projectStatus = projectStatus;
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

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonIgnore
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

	@JsonProperty("projectCreationDate")
	public LocalDateTime getProjectCreationDate() {
		return projectCreationDate;
	}

	@JsonIgnore
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
				+ projectManager + ", projectMemebers=" + projectMembers + ", projectCreationDate="
				+ projectCreationDate + ", projectStatus=" + projectStatus + "]";
	}

}
