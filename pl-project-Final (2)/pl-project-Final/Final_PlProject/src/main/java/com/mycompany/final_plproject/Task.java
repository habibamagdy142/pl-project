package com.mycompany.final_plproject;

public class Task {

    private int taskId;
    private int empId;
    private String description;
    private double completedHours;
    private double estimatedHours;

    public Task(int taskId, int empId, String description,
                double completedHours, double estimatedHours) {

        this.taskId = taskId;
        this.empId = empId;
        this.description = description;
        this.completedHours = completedHours;
        this.estimatedHours = estimatedHours;
    }

    public double getProgressFraction() {
        if (estimatedHours == 0) return 0;
        return completedHours / estimatedHours;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public double getCompletedHours() {
        return completedHours;
    }

    public String getDescription() {
        return description;
    }
}

























