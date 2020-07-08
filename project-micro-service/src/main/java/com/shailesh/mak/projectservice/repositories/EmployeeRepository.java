package com.shailesh.mak.projectservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shailesh.mak.projectservice.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

	Optional<Employee> findByEmployeeId(String employeeId);

	Optional<Employee> findByFirstName(String firstName);

	Optional<Employee> findByLastName(String lastName);
}
