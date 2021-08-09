package com.employee.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employee.domain.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {

	@Query("SELECT e FROM Employees e WHERE e.id = :id")
	public List<Employees> findById(@Param("id") String id);

	@Modifying
	@Query("delete from Employees e WHERE e.id = :id")
	public void deleteById(@Param("id") String id);

	@Query("SELECT e FROM Employees e WHERE e.login= :login")
	public List<Employees> findByLogin(@Param("login") String login);
	
	@Query("SELECT e FROM Employees e WHERE e.salary between :minSalary AND :maxSalary")
	public List<Employees> findAllBySalary(@Param("minSalary") double minSalary, @Param("maxSalary") double maxSalary);

	Page<Employees> findAllByNameContains(String name, Pageable pageable);

}
