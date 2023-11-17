package com.exercise.employeeapp.employee.service;

import com.exercise.employeeapp.employee.duration.calculator.DurationResult;
import com.exercise.employeeapp.employee.duration.calculator.EmploymentDurationCalculator;
import com.exercise.employeeapp.employee.entity.Employee;
import com.exercise.employeeapp.employee.exception.DuplicateEmployeeException;
import com.exercise.employeeapp.employee.mapper.EmployeeDetailsTaxInfoMapper;
import com.exercise.employeeapp.employee.model.EmployeeDetailsWithTaxInfo;
import com.exercise.employeeapp.employee.repository.EmployeeRepository;
import com.exercise.employeeapp.tax.TaxCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@Service
public class EmployeeService {

    Logger log = LoggerFactory.getLogger("EmployeeService");

    private final EmployeeRepository employeeRepository;

    static final double MINIMUM_YEARLY_SALARY_TO_COLLECT_CESS = 2500000;

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

    public EmployeeDetailsWithTaxInfo getTaxInfoFor(String employeeId, short financialYear) {

        Optional<Employee> optionalEmployee = employeeRepository.findEmployeeByEmployeeId(employeeId);

        if (optionalEmployee.isPresent()) {
            return calculateTaxForEmployee(optionalEmployee.get(), financialYear);
        } else {
            EmployeeDetailsWithTaxInfo employeeDetailsWithTaxInfo = new EmployeeDetailsWithTaxInfo();
            employeeDetailsWithTaxInfo.setEmployeeId(employeeId);
            return employeeDetailsWithTaxInfo;
        }

    }

    private EmployeeDetailsWithTaxInfo calculateTaxForEmployee(Employee employee, short financialYear) {
        double totalSalaryForYear = getTotalSalaryForTheFinancialYearBasedOnDateOfJoining(employee.getDoj(), employee.getMonthlySalary(), financialYear);
        double taxForTheFY = TaxCalculator.calculateTax(totalSalaryForYear, 0.00);
        double cessAmount = 0.0d;

        if (totalSalaryForYear > MINIMUM_YEARLY_SALARY_TO_COLLECT_CESS) {
            cessAmount = TaxCalculator.calculateCess(totalSalaryForYear);
        }

        return EmployeeDetailsTaxInfoMapper.mapEmployeeDetailsTaxInfoToEmployeeDetailsWithTaxInfo(employee,
                totalSalaryForYear, taxForTheFY, cessAmount);

    }

    private Double getTotalSalaryForTheFinancialYearBasedOnDateOfJoining(LocalDate dateOfJoining, Double monthlySalary, short financialYear) {
        DurationResult durationResult = EmploymentDurationCalculator.getEmploymentDuration(dateOfJoining, financialYear, Month.APRIL);
        double totalSalary;

        int monthsWorked = durationResult.getMonths();
        int daysWorked = durationResult.getDays();

        log.info("Employment Duration Result ");
        log.info("Months = " + monthsWorked);
        log.info("Days = " + daysWorked);

        if (monthsWorked >= 12) {
            totalSalary = 12 * monthlySalary;
        } else {
            totalSalary = (monthsWorked * monthlySalary) + ((daysWorked * monthlySalary) / 30);
        }

        return totalSalary;
    }

}
