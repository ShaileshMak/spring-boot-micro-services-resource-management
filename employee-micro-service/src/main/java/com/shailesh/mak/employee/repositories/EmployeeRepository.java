package com.shailesh.mak.employee.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.shailesh.mak.employee.models.Employee;

@Repository
public interface EmployeeRepository extends CassandraRepository<Employee, UUID>{
	@AllowFiltering
	Optional<Employee> findByEmployeeId(String employeeId);
	
	@AllowFiltering
	Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

}
