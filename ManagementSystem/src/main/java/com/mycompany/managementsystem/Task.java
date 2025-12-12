/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.managementsystem;

/**
 *
 * @author hp
 */
// الاول هنعمل كلاس اسمه تاسك جوه الكلاس تيم ليدر و هيكون فيه معلومات عن ال assign & view completed tasks 
   public class Task{
   private int taskID;
   private int empID;
    private String taskId;
    private String taskDescription;
    private String taskStatus;

    public Task(int taskID, int empID, String taskId, String taskDescription, String taskStatus) {
        this.taskID = taskID;
        this.empID = empID;
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public String getTaskId() {
        return taskId;
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
