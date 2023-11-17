package com.exercise.employeeapp.employee.duration.calculator;

public class DurationResult {
    private final int months;
    private final int days;

    public DurationResult(int months, int days) {
        this.months = months;
        this.days = days;
    }

    public int getMonths() {
        return months;
    }

    public int getDays() {
        return days;
    }
}