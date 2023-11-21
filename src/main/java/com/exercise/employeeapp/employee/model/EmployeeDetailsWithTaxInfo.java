package com.exercise.employeeapp.employee.model;

import java.io.Serializable;

public class EmployeeDetailsWithTaxInfo implements Serializable {

    //employee code, first name, last name, yearly salary, tax amount, cess amount
    String firstName;
    String lastName;
    String employeeId;
    Double yearlySalary;
    Double yearlyTaxAmount;
    Double cessAmount;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Double getYearlySalary() {
        return yearlySalary;
    }

    public void setYearlySalary(Double yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public Double getYearlyTaxAmount() {
        return yearlyTaxAmount;
    }

    public void setYearlyTaxAmount(Double yearlyTaxAmount) {
        this.yearlyTaxAmount = yearlyTaxAmount;
    }

    public Double getCessAmount() {
        return cessAmount;
    }

    public void setCessAmount(Double cessAmount) {
        this.cessAmount = cessAmount;
    }

}
