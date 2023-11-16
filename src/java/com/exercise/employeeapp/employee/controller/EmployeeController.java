package com.exercise.employeeapp.employee.controller;

import com.exercise.employeeapp.employee.entity.Employee;
import com.exercise.employeeapp.employee.exception.DuplicateEmployeeException;
import com.exercise.employeeapp.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    Logger log = LoggerFactory.getLogger("EmployeeController");

    private final EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    ResponseEntity<?> saveEmployee(@RequestBody @Valid Employee employee) {
       log.info("Save Employee API hit");
        try {
            return ResponseEntity.ok(employeeService.saveEmployee(employee));
        } catch (DuplicateEmployeeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee ID must be unique");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation: " + ex.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please contact the API Administrator");
        }
    }

}
