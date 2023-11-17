package com.exercise.employeeapp.employee.mapper;

import com.exercise.employeeapp.employee.entity.Employee;
import com.exercise.employeeapp.employee.model.EmployeeDetailsWithTaxInfo;

public class EmployeeDetailsTaxInfoMapper {
    public static EmployeeDetailsWithTaxInfo mapEmployeeDetailsTaxInfoToEmployeeDetailsWithTaxInfo(final Employee employee,
                                                                                                   final double totalSalary,
                                                                                                   final double tax,
                                                                                                   final double cessAmount) {
        EmployeeDetailsWithTaxInfo employeeDetailsWithTaxInfo = new EmployeeDetailsWithTaxInfo();
        employeeDetailsWithTaxInfo.setEmployeeId(employee.getEmployeeId());
        employeeDetailsWithTaxInfo.setFirstName(employee.getFirstName());
        employeeDetailsWithTaxInfo.setLastName(employee.getLastName());
        employeeDetailsWithTaxInfo.setYearlySalary(totalSalary);
        employeeDetailsWithTaxInfo.setYearlyTaxAmount(tax);
        employeeDetailsWithTaxInfo.setCessAmount(cessAmount);
        return employeeDetailsWithTaxInfo;
    }
}
