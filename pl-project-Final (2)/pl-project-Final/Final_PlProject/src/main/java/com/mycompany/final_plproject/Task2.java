package com.mycompany.final_plproject;

// الاول هنعمل كلاس اسمه تاسك جوه الكلاس تيم ليدر و هيكون فيه معلومات عن ال assign & view completed tasks 
   public class Task2{
   private int taskID;
   private int empID;
    private String taskDescription;
    private String taskStatus;

    public Task2(int taskID, int empID, String taskDescription, String taskStatus) {
        this.taskID = taskID;
        this.empID = empID;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public int getTaskID() {
        return taskID;
    }
    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
   }