package com.exercise.employeeapp.employee.duration.calculator;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public interface EmploymentDurationCalculator {
    static DurationResult getEmploymentDuration(LocalDate doj, int financialYearStartYear, Month financialYearStartMonth) {
        // Financial year start and end dates
        LocalDate financialYearStart = LocalDate.of(financialYearStartYear, financialYearStartMonth, 1);
        LocalDate financialYearEnd = financialYearStart.plusYears(1).minusDays(1);

        // Calculate the duration of employment
        Period periodOfEmployment = Period.between(doj, LocalDate.now());

        // Check if the employee was employed within the financial year
        if (doj.isAfter(financialYearEnd)) {
            // Employee started after the financial year
            return new DurationResult(0, 0);
        }

        // Calculate the exact number of months and days of employment
        int months = periodOfEmployment.getYears() * 12 + periodOfEmployment.getMonths();
        int days = periodOfEmployment.getDays();

        // Check if the employee is still employed within the financial year
        if (doj.plusMonths(months).plusDays(days).isAfter(financialYearEnd)) {
            // Adjust the months and days if the employment extends beyond the financial year
            months = (int) (ChronoUnit.MONTHS.between(doj, financialYearEnd) + 1);
            days = (int) doj.plusMonths(months).until(financialYearEnd.plusDays(1), ChronoUnit.DAYS);
        }

        return new DurationResult(months, days);
    }
}
