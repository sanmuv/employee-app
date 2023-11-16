package com.exercise.employeeapp.employee.service;

import com.exercise.employeeapp.employee.entity.Employee;
import com.exercise.employeeapp.employee.exception.DuplicateEmployeeException;
import com.exercise.employeeapp.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    Logger log = LoggerFactory.getLogger("EmployeeService");

    private final EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        log.info("About to save Employee \n" + employee.toString() + "\n");

        try {
            return employeeRepository.save(employee);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmployeeException("Employee ID must be unique", ex);
        }
    }
}
