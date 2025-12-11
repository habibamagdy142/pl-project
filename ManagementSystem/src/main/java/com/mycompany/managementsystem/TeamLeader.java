package com.mycompany.managementsystem;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TeamLeader extends User {

    private String projectId;
    //team members file
    File file = new File("E:\\1\\teams.txt");
    //employee file
    File empFile = new File("E:\\1\\emp.txt");

    public TeamLeader(int userId, String userName, String password, String email, String fullName) {
        super(userId, userName, password, email, fullName, "Team_Leader");
    }
    //manage employees
    //1.add members to the team

    public void addTeamMember() {
        //diplay all employees
        try (Scanner fileScanner = new Scanner(empFile)) {
            while (fileScanner.hasNext()) {
                String firstName = fileScanner.next();
                String lastName = fileScanner.next();
                int empId = fileScanner.nextInt();

                System.out.println(firstName + " " + lastName + " , " + empId);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Employee file not found!");
            return;
        }

        Scanner input = new Scanner(System.in);

        // teamLeader enter the name of employee to add
        System.out.print("Enter employee name: ");
        String name = input.nextLine();

        // teamLeader enter the id of employee
        System.out.print("Enter employee ID: ");
        int id;
        try {
            id = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID format! Must be a number.");
            return;
        }
        //write the name and id of the employee in teamMembers file
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(id + "," + name);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        System.out.println("Employee added successfully!");
    }

    public void deleteTeamMember() {
        Scanner input = new Scanner(System.in);
        //list that will include all team Members in team Members text file
        List<String> lines = new ArrayList<>();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: team members file not found.");
            return;
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // show the list of team Members
        System.out.println("Team Members:");
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                System.out.println(line);
            }
        }

        // team leader enter the name of teamMember he want to delete
        System.out.print("Enter name to delete: ");
        String name = input.nextLine();
        // team leader enter the id of teamMember he want to delete
        System.out.print("Enter ID to delete: ");
        int id;
        try {
            id = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID format! Must be a number.");
            return;
        }

        // the list that will include the team members after deletion
        List<String> updated = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");
            if (parts.length < 2) {
                System.out.println("Warning: invalid line format skipped.");
                continue;
            }

            int empId;
            try {
                empId = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                System.out.println("Warning: invalid ID in file skipped.");
                continue;
            }

            String empName = parts[1].trim();

            // if teamMember id and name found skip(dont add them in the updated list)
            if (empId == id && empName.equalsIgnoreCase(name)) {
                found = true;
                continue;
            }

            updated.add(line);
        }

        // overwrite the updated list in the teamMember text file
        try (PrintWriter writer = new PrintWriter(file)) {
            for (String l : updated) {
                writer.println(l);
            }
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            return;
        }

        if (found) {
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Employee not found.");
        }
    }

    //assign task
    public void assignTask(int empID, String description, String deadline) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tasks.txt", true))) {

            int newID = generateTaskID();
         //الفواصل ديه عشان تفصل بين البيانات في الملف
            String record = newID + ","
                    + empID + ","
                    + description + ","
                    + "Pending,"
                    + deadline + ",-";

            bw.write(record);
            bw.newLine();

            System.out.println("Task assigned successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int generateTaskID() {
        int lastID = 200;

        try (BufferedReader br = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                lastID = Integer.parseInt(data[0]);
            }
        } catch (Exception e) {
            // لو الملف فاضي مفيش مشكلة
        }

        return lastID + 1;
    }

    //view completed tasks
    public List<Task> viewCompletedTasks() {
        // بترجع List من نوع Task
        // بننشئ ليست فاضية علشان نخزن فيها كل المهام اللي خلصت (Completed)
        List<Task> completedTasks = new ArrayList<>();
        // بنقرا من ملف المهام using BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader("tasks.txt"))) {
            // بنقرأ كل سطر في الملف,and each line contauns a task in the file
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                //101[0],12[1],Fix bug login[2],Completed[3]
                if (data[3].equals("Completed")) {
                    // بنشيك اذا كانت حالة المهمة "Completed"
                    //change each one to the data type that fits it 
                    int taskID = Integer.parseInt(data[0]);
                    int empID = Integer.parseInt(data[1]);
                    String taskId = data[0];
                    String taskDescription = data[2];
                    String taskStatus = data[3];
                    //new object of type task
                    Task task = new Task(taskID, empID, taskId, taskDescription, taskStatus);
                    completedTasks.add(task);
                }
            }
            //error handling 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return completedTasks;
    }
}


