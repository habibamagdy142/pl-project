package com.mycompany.final_plproject;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Employee extends User {

    private boolean isClockedIn;
    private String clockInTime;

    public File attendanceFile = new File("text files\\attendance.txt");
    public File tasksFile = new File("text files\\tasks.txt");
    public File vacationFile = new File("text files\\vacation.txt");
    public File penaltiesFile = new File("text files\\penalties.txt");
     
    public Employee(){ } ;
    public Employee(int userId, String userName, String password,
                    String email, String fullName) {

        super(userId, userName, password, email, fullName, Role.EMPLOYEE);
        this.isClockedIn = false;
        this.clockInTime = null;
    }

    // ===== 1. Clock In =====
    public void clockIn() {
        if (isClockedIn) {
            System.out.println("You are already clocked in!");
            return;
        }

        clockInTime = getCurrentDateTime();
        isClockedIn = true;

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(attendanceFile, true))) {

            writer.println(getUserID() + "," + getUserName() + ","
                    + clockInTime + ",IN");

            System.out.println("Clocked in at: " + clockInTime);
            System.out.println("Attendance recorded in file");

        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }

    // ===== 2. Clock Out =====
    public void clockOut() {
        if (!isClockedIn) {
            System.out.println("You are not clocked in!");
            return;
        }

        String clockOutTime = getCurrentDateTime();
        isClockedIn = false;

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(attendanceFile, true))) {

            writer.println(getUserID() + "," + getUserName() + ","
                    + clockOutTime + ",OUT");

            System.out.println("Clocked out at: " + clockOutTime);

            if (clockInTime != null) {
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                LocalDateTime inTime =
                        LocalDateTime.parse(clockInTime, formatter);
                LocalDateTime outTime =
                        LocalDateTime.parse(clockOutTime, formatter);

                long minutesWorked =
        Duration.between(inTime, outTime).toMinutes();

         double hoursWorked = minutesWorked / 60.0;


                System.out.println("Worked: " + hoursWorked + " hours");
            }

            clockInTime = null;

        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }

    // ===== 3. View My Tasks =====
    public void viewTasks() {

        List<String> myTasks = new ArrayList<>();

        try (Scanner scanner = new Scanner(tasksFile)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (Integer.parseInt(data[1]) == getUserID()) {
                    myTasks.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Tasks file not found!");
            return;
        }

        if (myTasks.isEmpty()) {
            System.out.println("No tasks assigned.");
            return;
        }

        System.out.println("Your Tasks:");
        for (int i = 0; i < myTasks.size(); i++) {
            String[] data = myTasks.get(i).split(",");
            System.out.println(
                    (i + 1) + ". ID: " + data[0]
                            + ", Description: " + data[2]
                            + ", Status: " + data[3]
                            +", Deadline : " + data[4]
                            +  ", Estimated Hours: " + data[6]
                            + ", Completed Hours: " + data[5]

            );
        }
    }

    // ===== 4. Complete Task =====
  public void completeTask(int taskNumber) {

    List<String> allTasks = new ArrayList<>();
    List<String> myTasks = new ArrayList<>();

    try (Scanner scanner = new Scanner(tasksFile)) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            if (Integer.parseInt(data[1]) == getUserID()) {
                myTasks.add(line);
            }

            allTasks.add(line);
        }

    } catch (FileNotFoundException e) {
        System.out.println("Tasks file not found!");
        return;
    }

    if (myTasks.isEmpty()) {
        System.out.println("You have no tasks.");
        return;
    }

    if (taskNumber < 1 || taskNumber > myTasks.size()) {
        System.out.println("Invalid task number!");
        return;
    }

    String oldTask = myTasks.get(taskNumber - 1);
    String[] d = oldTask.split(",");

    // taskId,empId,desc,status,deadline,completed,estimated
    String updatedTask =
            d[0] + "," + d[1] + "," + d[2]
                    + ",Completed," + d[4] + "," + d[6] + "," + d[6];

    for (int i = 0; i < allTasks.size(); i++) {
        if (allTasks.get(i).equals(oldTask)) {
            allTasks.set(i, updatedTask);
            break;
        }
    }

    try (PrintWriter writer = new PrintWriter(tasksFile)) {
        for (String t : allTasks) {
            writer.println(t);
        }
        System.out.println("Task marked as COMPLETED");

    } catch (IOException e) {
        System.out.println("Error updating task.");
    }
}



  
   public void viewPenalties() {
        List<String> myPenalties = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(penaltiesFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 4 && data[1].equals(String.valueOf(getUserID()))) {
                    myPenalties.add(line);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Penalties file not found!");
            return;
        }
        
        if (myPenalties.isEmpty()) {
            System.out.println("No penalties recorded.");
        } else {
            System.out.println("Your Penalties:");
            for (int i = 0; i < myPenalties.size(); i++) {
                String[] data = myPenalties.get(i).split(",");
                System.out.println((i + 1) + ". Date: " + data[2] + ", Reason: " + data[3]);
            }
        }
    }
    
    // ===== Update Task Progress =====
 public static void updateCompletedHours(int taskId, double newCompletedHours) {

    File file = new File("text files\\tasks.txt");

    ArrayList<String> tasks = new ArrayList<>();
    boolean found = false;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            tasks.add(line);
        }
    } catch (Exception e) {
        System.out.println("Error reading tasks file");
        return;
    }

    for (int i = 0; i < tasks.size(); i++) {

        String[] data = tasks.get(i).split(",");

        int fileTaskId = Integer.parseInt(data[0]);

        if (fileTaskId == taskId) {

            double estimated = Double.parseDouble(data[6]);

            data[5] = String.valueOf(newCompletedHours);

            if (newCompletedHours >= estimated) {
                data[3] = "Completed";
            } else {
                data[3] = "InProgress";
            }

            tasks.set(i, String.join(",", data));
            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println("Task not found!");
        return;
    }

    try (PrintWriter pw = new PrintWriter(file)) {
        for (String t : tasks) {
            pw.println(t);
        }
    } catch (Exception e) {
        System.out.println("Error writing tasks file");
        return;
    }

    System.out.println("Task updated successfully!");
}

    private String getCurrentDateTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }

  


    private String getCurrentDate() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(formatter);
    }
    
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
 
    public void requestVacation(String startDate, String endDate, String reason) {

    if (!employeeExists(getUserID())) {
        System.out.println("Vacation request failed: Employee does not exist.");
        return;
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(vacationFile, true))) {
        writer.println(getUserID() + "," + getFullName() + "," +
                       startDate + "," + endDate + "," + reason + ",Pending");
        System.out.println("Vacation requested successfully");
    } catch (IOException e) {
        System.out.println("Error saving vacation request: " + e.getMessage());
    }
}

       
    }
