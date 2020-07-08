package com.shailesh.mak.projectservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shailesh.mak.projectservice.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	Optional<Project> findByProjectName(String projectName);
}
