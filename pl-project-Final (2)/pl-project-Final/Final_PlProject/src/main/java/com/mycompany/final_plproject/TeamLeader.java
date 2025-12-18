package com.mycompany.final_plproject ; 

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;


public class TeamLeader extends User {
     File penaltiesFile =new File("text files\\penalties.txt");
     File vacationFile = new File("text files\\vacation.txt");


    private String projectId;

    public TeamLeader(int par) {};
    

    public TeamLeader(int userId, String userName, String password, String email, String fullName) {
        super(userId, userName, password, email, fullName, Role.TEAM_LEADER);
    }

    //team members file
    File Teamfile = new File("text files\\teams.txt");
    //employee file
    File empFile = new File("text files\\employee.txt");
    //manage employees
    //1.add members to the team
    
    
private boolean employeeExists(int empID) {

    File usersFile = new File("text files\\users.txt");

    if (!usersFile.exists()) {
        System.out.println("Users file not found!");
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {

        String line;
        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            if (data.length < 6) {
                continue; 
            }

            int fileID = Integer.parseInt(data[0].trim());
            String role = data[5].trim();

            if (fileID == empID && role.equalsIgnoreCase("EMPLOYEE")) {
                return true;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}


    
    private boolean taskAlreadyAssigned(int empID, String description) {
     
    File tasksFile = new File("text files\\tasks.txt");
    
    if (!tasksFile.exists()) {
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(tasksFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] d = line.split(",");

            int fileEmpID = Integer.parseInt(d[1].trim());
            String fileDesc = d[2].trim();

            if (fileEmpID == empID &&
                fileDesc.equalsIgnoreCase(description)) {
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}


    public void addTeamMember() {
        //create file
        try {
            if (!empFile.exists()) {
                empFile.createNewFile();
                System.out.println("File created successfully!");
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
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
        System.out.print("Enter employee first name: ");
        String fName = input.nextLine();
        System.out.print("Enter employee last name: ");
        String lName = input.nextLine();

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
        try (PrintWriter writer = new PrintWriter(new FileWriter(Teamfile, true))) {
            writer.println(fName + " " + lName + "," + id);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return;
        }

        System.out.println("Employee added successfully!");
    }

    public void deleteTeamMember() {

        // Create file if not exists
        try {
            if (!Teamfile.exists()) {
                Teamfile.createNewFile();
                System.out.println("File created successfully!");
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }

        Scanner input = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        // Load file content
        try (Scanner reader = new Scanner(Teamfile)) {
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error reading team file.");
            return;
        }

        // Display members
        System.out.println("Team Members:");
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                System.out.println(line);
            }
        }

        // Input
        System.out.print("Enter the first name of employee to delete: ");
        String fName = input.nextLine();

        System.out.print("Enter the last name of employee to delete: ");
        String lName = input.nextLine();

        System.out.print("Enter the ID to delete: ");

        int id;
        try {
            id = input.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid ID format! Must be a number.");
            return;
        }

        List<String> updated = new ArrayList<>();
        boolean found = false;

        for (String line : lines) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");

            if (parts.length != 2) {
                continue;
            }

            String[] nameParts = parts[0].trim().split(" ");
            if (nameParts.length != 2) {
                continue;
            }

            String fileFName = nameParts[0];
            String fileLName = nameParts[1];

            int fileID = Integer.parseInt(parts[1].trim());

            // Found match → skip (delete)
            if (fileFName.equalsIgnoreCase(fName)
                    && fileLName.equalsIgnoreCase(lName)
                    && fileID == id) {

                found = true;
                continue;
            }

            updated.add(line);
        }

        // لو ال employee مش موجود مش هيمسح وهيطلع error msg
        if (!found) {
            System.out.println("Error: Employee does NOT exist in team file.");
            return;
        }

        // overwrite the list after deletion
        try (PrintWriter writer = new PrintWriter(Teamfile)) {
            for (String l : updated) {
                writer.println(l);
            }
        } catch (Exception e) {
            System.out.println("Error writing file.");
            return;
        }

        System.out.println("Employee deleted successfully!");
    }
    

    
  public void assignTask(int empID, String description,
                       String deadline, double estimatedHours) {

    // 1️⃣ employee exists ?
    if (!employeeExists(empID)) {
        System.out.println("Employee does not exist!");
        return;
    }

    // 2️⃣ duplicate task ?
    if (taskAlreadyAssigned(empID, description)) {
        System.out.println("Task already assigned to this employee!");
        return ;
    }
    File tasksFile = new File("text files\\tasks.txt");

    try (BufferedWriter bw = new BufferedWriter(
            new FileWriter(tasksFile, true))) {

        int newID = generateTaskID();

        String record =
                newID + "," +
                empID + "," +
                description + "," +
                "Pending," +
                deadline + "," +
                0 + "," +
                estimatedHours;

        bw.write(record);
        bw.newLine();

        System.out.println("Task assigned successfully!");

    } catch (IOException e) {
        System.out.println("Error assigning task!");
    }
         return ;
         
  }


    private int generateTaskID() {
        int lastID = 200;

        try (BufferedReader br = new BufferedReader(new FileReader("text files\\tasks.txt"))) {
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
    public List<Task2> viewCompletedTasks() {
        // بترجع List من نوع Task
        // بننشئ ليست فاضية علشان نخزن فيها كل المهام اللي خلصت (Completed)
        List<Task2> completedTasks = new ArrayList<>();
        // بنقرا من ملف المهام using BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader("text files\\tasks.txt"))) {
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
                    String taskDescription = data[2];
                    String taskStatus = data[3];
                    //new object of type task
                    Task2 task = new Task2(taskID, empID, taskDescription, taskStatus);
                    completedTasks.add(task);
                }

            }

            //error handling 
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (completedTasks.isEmpty()) {
            System.out.println("No completed tasks found.");
        } else {
            for (Task2 t : completedTasks) {
                System.out.println(t.getTaskID() + " - " + t.getTaskDescription());
            }

        }
        return completedTasks;
    }
    private int generatePenaltyId() {

    int lastId = 300;

    try (BufferedReader br = new BufferedReader(new FileReader("text files\\penalties.txt"))) {

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            lastId = Integer.parseInt(data[0]);
        }

    } catch (Exception e) {
    }

    return lastId + 1;
}

    public void addPenalty(int empId, String reason) {

    if (!employeeExists(empId)) {
        System.out.println("Error: Employee does NOT exist!");
        return;
    }

    try (PrintWriter writer =
            new PrintWriter(new FileWriter(penaltiesFile, true))) {

        String date = LocalDate.now().toString();

        writer.println(
                generatePenaltyId() + "," +
                empId + "," +
                date + "," +
                reason
        );

        System.out.println("Penalty added successfully");

    } catch (IOException e) {
        System.out.println("Error saving penalty.");
    }
}

public void respondToVacationRequest(int employeeId, boolean isApproved, String comment) {

    File tempFile = new File("temp_vacations.txt");
    boolean updated = false;

    try (
        BufferedReader reader = new BufferedReader(new FileReader(vacationFile));
        PrintWriter writer = new PrintWriter(new FileWriter(tempFile))
    ) {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            int id = Integer.parseInt(parts[0]);

            if (id == employeeId && parts[5].equals("Pending")) {

                String status = isApproved ? "Approved" : "Rejected";

                writer.println(
                    parts[0] + "," +
                    parts[1] + "," +
                    parts[2] + "," +
                    parts[3] + "," +
                    parts[4] + "," +
                    status + "," +
                    comment
                );

                updated = true;

            } else {
                writer.println(line);
            }
        }

    } catch (IOException e) {
        System.out.println("Error updating vacation request.");
        return;
    }

    vacationFile.delete();
    tempFile.renameTo(vacationFile);

    if (updated) {
        System.out.println("Vacation request processed successfully.");
    } else {
        System.out.println("No pending vacation request found for this employee.");
    }
}



    
}