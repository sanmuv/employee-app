package com.exercise.employeeapp.employee.repository;

import com.exercise.employeeapp.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.employeeId = (:employeeId)")
    Optional<Employee> findEmployeeByEmployeeId(@Param("employeeId") String employeeId);
}
