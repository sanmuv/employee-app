package com.exercise.employeeapp.employee.controller;

import com.exercise.employeeapp.employee.entity.Employee;
import com.exercise.employeeapp.employee.exception.DuplicateEmployeeException;
import com.exercise.employeeapp.employee.model.EmployeeDetailsWithTaxInfo;
import com.exercise.employeeapp.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please contact the API Administrator");
        }
    }

    @GetMapping("/{empid}/annual/tax/{finyearstart}")
    ResponseEntity<?> getTaxDeductionForAllEmployees(@PathVariable String empid,
                                                     @PathVariable short finyearstart) {
        EmployeeDetailsWithTaxInfo employeeDetailsWithTaxInfo = employeeService.getTaxInfoFor(empid, finyearstart);

        int currentYear = LocalDate.now().getYear();
        if(finyearstart < currentYear - 2 || finyearstart > currentYear ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Financial year value is either in the past or in future. Can only be the current FY");
        }

        if(employeeDetailsWithTaxInfo.getFirstName() !=null) {
            return ResponseEntity.status(HttpStatus.OK).body(employeeDetailsWithTaxInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with the employeeId " + empid);
        }
    }

}
