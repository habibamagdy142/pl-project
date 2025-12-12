/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.managementsystem;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class ManagementSystem {

    public static void main(String[] args) {
        TeamLeader t1 = new TeamLeader();
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== TeamLeader Menu ====");
            System.out.println("1. Add Team Member");
            System.out.println("2. Delete Team Member");
            System.out.println("3. Assign Task");
            System.out.println("4. View Completed Tasks");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            choice = input.nextInt();
            input.nextLine(); // clear newline

            switch (choice) {
                case 1:
                    t1.addTeamMember();
                    break;
                case 2:
                    t1.deleteTeamMember();
                    break;
                case 3:
                    System.out.print("Enter Employee ID: ");
                    int empID = input.nextInt();
                    input.nextLine(); // clear newline
                    System.out.print("Enter Task Description: ");
                    String desc = input.nextLine();
                    System.out.print("Enter Deadline: ");
                    String deadline = input.nextLine();
                    t1.assignTask(empID, desc, deadline);
                    break;
                case 4:
                    List<Task> completed = t1.viewCompletedTasks();
                    System.out.println("\nCompleted Tasks:");
                    for (Task t : completed) {
                        System.out.println(t.getTaskId() + " - " + t.getTaskDescription() + " - " + t.getTaskStatus());
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);

        input.close();
    }
        
    }
    

