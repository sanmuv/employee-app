package com.exercise.employeeapp.tax;


public interface TaxCalculator {

    static double calculateTax(double yearlySalary, double tax) {
        if (yearlySalary >= 1000001) {
            tax = (20 * (yearlySalary - 1000000)) / 100;
            yearlySalary = yearlySalary - 1000000;
        } else if (yearlySalary >= 500001) {
            tax = (10 * (yearlySalary - 500000)) / 100;
            yearlySalary = yearlySalary - 500000;
        } else if (yearlySalary >= 250001) {
            tax = (5 * (yearlySalary - 250000)) / 100;
            yearlySalary = yearlySalary - 250000;
        } else {
            return tax;
        }
        return tax + calculateTax(yearlySalary, tax);

    }

    static double calculateCess(double yearlySalary) {
        return (yearlySalary * 2) / 100;
    }

}
