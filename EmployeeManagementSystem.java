
package com.mycompany.mavenproject2;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Employee class
class Employee extends User {
    private LocalDateTime clockInTime;
    private boolean isClockedIn;
    private int totalHours;
    public List<String> attendanceRecords;
    public List<String> tasks;
    public List<String> vacationRequests;
    public List<String> penalties;
    
    public Employee(int id, String name, String username, String password) {
        super(id, name, "Employee", username, password);
        this.isClockedIn = false;
        this.totalHours = 0;
        this.attendanceRecords = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.vacationRequests = new ArrayList<>();
        this.penalties = new ArrayList<>();
    }
    
    // ===== 1. Clock In (Current Time) =====
    public String clockIn() {
        if (isClockedIn) {
            return "You are already clocked in!";
        }
        clockInTime = LocalDateTime.now();
        isClockedIn = true;
        return "Clocked in at: " + formatDateTime(clockInTime);
    }
    
    // ===== 2. Clock Out (Current Time) =====
    public String clockOut() {
        if (!isClockedIn) {
            return "You are not clocked in!";
        }
        LocalDateTime clockOutTime = LocalDateTime.now();
        long hoursWorked = Duration.between(clockInTime, clockOutTime).toHours();
        totalHours += hoursWorked;
        
        String record = formatDateTime(clockInTime) + " to " + formatDateTime(clockOutTime) + " - " + hoursWorked + " hours";
        attendanceRecords.add(record);
        
        isClockedIn = false;
        clockInTime = null;
        
        return "Clocked out at: " + formatDateTime(clockOutTime) + "\nWorked: " + hoursWorked + " hours\nTotal hours: " + totalHours;
    }
    
    // ===== 3. Manual Clock In =====
    public String manualClockIn(String dateTimeStr) {
        if (isClockedIn) {
            return "You are already clocked in!";
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            clockInTime = LocalDateTime.parse(dateTimeStr, formatter);
            isClockedIn = true;
            return "Manually clocked in at: " + dateTimeStr;
        } catch (Exception e) {
            return "Invalid format! Use: YYYY-MM-DD HH:mm";
        }
    }
    
    // ===== 4. Manual Clock Out =====
    public String manualClockOut(String dateTimeStr) {
        if (!isClockedIn) {
            return "You are not clocked in!";
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime clockOutTime = LocalDateTime.parse(dateTimeStr, formatter);
            
            if (clockOutTime.isBefore(clockInTime)) {
                return "Clock out time must be after clock in time!";
            }
            
            long hoursWorked = Duration.between(clockInTime, clockOutTime).toHours();
            totalHours += hoursWorked;
            
            String record = formatDateTime(clockInTime) + " to " + formatDateTime(clockOutTime) + " - " + hoursWorked + " hours";
            attendanceRecords.add(record);
            
            isClockedIn = false;
            clockInTime = null;
            
            return "Manually clocked out at: " + dateTimeStr + "\nWorked: " + hoursWorked + " hours\nTotal hours: " + totalHours;
        } catch (Exception e) {
            return "Invalid format! Use: YYYY-MM-DD HH:mm";
        }
    }
    
    // ===== 5. Request Vacation =====
    public String requestVacation(String startDate, String endDate, String reason) {
        vacationRequests.add("From: " + startDate + " To: " + endDate + " Reason: " + reason + " Status: Pending");
        return "Vacation requested from " + startDate + " to " + endDate;
    }
    
    // ===== 6. View My Tasks =====
    public String viewTasks() {
    if (tasks.isEmpty()) {
        return "No tasks assigned.";
    }
    
    StringBuilder sb = new StringBuilder("Your Tasks:\n");
    for (int i = 0; i < tasks.size(); i++) {
        sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
    }
    return sb.toString();
}
    
    // ===== 7. Complete a Task =====
    public String completeTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            return "Invalid task number!";
        }
        
        String task = tasks.get(taskNumber - 1);
        tasks.set(taskNumber - 1, task.replace("[Pending]", "[Completed]"));
        return "Task completed: " + task;
    }
    
    // ===== 8. Add a Task (Self-assign) =====
    public String addTask(String description) {
        tasks.add(description + " [Pending]");
        return "Task added: " + description;
    }
    
    // ===== 9. View My Penalties =====
    public String viewPenalties() {
    if (penalties.isEmpty()) {
        return "No penalties recorded.";
    }
    
    StringBuilder sb = new StringBuilder("Your Penalties:\n");
    for (int i = 0; i < penalties.size(); i++) {
        sb.append(i + 1).append(". ").append(penalties.get(i)).append("\n");
    }
    return sb.toString();
}
    // ===== 10. View Attendance Summary =====
    public String viewAttendanceSummary() {
        return "Employee: " + name + " (ID: " + id + ")\n" +
               "Total Hours: " + totalHours + "\n" +
               "Attendance Records: " + attendanceRecords.size() + "\n" +
               "Currently Clocked In: " + (isClockedIn ? "Yes" : "No") + "\n" +
               "Tasks: " + tasks.size() + "\n" +
               "Vacation Requests: " + vacationRequests.size() + "\n" +
               "Penalties: " + penalties.size();
    }
    
    // ===== 11. View Detailed Attendance =====
    public String viewDetailedAttendance() {
    if (attendanceRecords.isEmpty()) {
        return "No attendance records.";
    }
    
    StringBuilder sb = new StringBuilder("Attendance Records:\n");
    for (int i = 0; i < attendanceRecords.size(); i++) {
        sb.append("Record ").append(i + 1).append(": ").append(attendanceRecords.get(i)).append("\n");
    }
    return sb.toString();
}
    
    // ===== 12. View Vacation Requests =====
    public String viewVacationRequests() {
    if (vacationRequests.isEmpty()) {
        return "No vacation requests.";
    }
    
    StringBuilder sb = new StringBuilder("Vacation Requests:\n");
    for (int i = 0; i < vacationRequests.size(); i++) {
        sb.append("Request ").append(i + 1).append(": ").append(vacationRequests.get(i)).append("\n");
    }
    return sb.toString();
}
    
    // ===== File Handling Methods =====
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Employee ID: " + id);
            writer.println("Employee Name: " + name);
            writer.println("Total Hours: " + totalHours);
            writer.println("Clocked In: " + isClockedIn);
            if (isClockedIn && clockInTime != null) {
                writer.println("Clock In Time: " + formatDateTime(clockInTime));
            }
            
            writer.println("\n=== Attendance Records ===");
            for (String record : attendanceRecords) {
                writer.println(record);
            }
            
            writer.println("\n=== Tasks ===");
            for (String task : tasks) {
                writer.println(task);
            }
            
            writer.println("\n=== Vacation Requests ===");
            for (String request : vacationRequests) {
                writer.println(request);
            }
            
            writer.println("\n=== Penalties ===");
            for (String penalty : penalties) {
                writer.println(penalty);
            }
            
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
    
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("\n=== Data from " + filename + " ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
    
    // Helper methods
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
    
    private String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(formatter);
    }
}

// Main class
public class EmployeeManagementSystem {
    
    // Main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Employee employee = new Employee(1001, "John Doe", "johndoe", "password123");
        
        System.out.println("=== Employee Management System ===");
        System.out.println("Employee: " + employee.name + " (ID: " + employee.id + ")");
        
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Clock In (Current Time)");
            System.out.println("2. Clock Out (Current Time)");
            System.out.println("3. Manual Clock In");
            System.out.println("4. Manual Clock Out");
            System.out.println("5. Request Vacation");
            System.out.println("6. View My Tasks");
            System.out.println("7. Complete a Task");
            System.out.println("8. Add a Task (Self-assign)");
            System.out.println("9. View My Penalties");
            System.out.println("10. View Attendance Summary");
            System.out.println("11. View Detailed Attendance");
            System.out.println("12. View Vacation Requests");
            System.out.println("13. Save Data to File");
            System.out.println("14. Load Data from File");
            System.out.println("15. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1 -> System.out.println(employee.clockIn());
                        
                    case 2 -> System.out.println(employee.clockOut());
                        
                    case 3 -> {
                        System.out.print("Enter date and time (YYYY-MM-DD HH:mm): ");
                        String clockInTime = scanner.nextLine();
                        System.out.println(employee.manualClockIn(clockInTime));
                    }
                        
                    case 4 -> {
                        System.out.print("Enter date and time (YYYY-MM-DD HH:mm): ");
                        String clockOutTime = scanner.nextLine();
                        System.out.println(employee.manualClockOut(clockOutTime));
                    }
                        
                    case 5 -> {
                        System.out.print("Enter start date (YYYY-MM-DD): ");
                        String startDate = scanner.nextLine();
                        System.out.print("Enter end date (YYYY-MM-DD): ");
                        String endDate = scanner.nextLine();
                        System.out.print("Enter reason: ");
                        String reason = scanner.nextLine();
                        System.out.println(employee.requestVacation(startDate, endDate, reason));
                    }
                        
                    case 6 -> System.out.println(employee.viewTasks());
                        
                    case 7 -> {
                        System.out.println(employee.viewTasks());
                        System.out.print("Enter task number to complete: ");
                        int taskNum = Integer.parseInt(scanner.nextLine());
                        System.out.println(employee.completeTask(taskNum));
                    }
                        
                    case 8 -> {
                        System.out.print("Enter task description: ");
                        String taskDesc = scanner.nextLine();
                        System.out.println(employee.addTask(taskDesc));
                    }
                        
                    case 9 -> System.out.println(employee.viewPenalties());
                        
                    case 10 -> System.out.println(employee.viewAttendanceSummary());
                        
                    case 11 -> System.out.println(employee.viewDetailedAttendance());
                        
                    case 12 -> System.out.println(employee.viewVacationRequests());
                        
                    case 13 -> {
                        System.out.print("Enter filename to save: ");
                        String saveFile = scanner.nextLine();
                        employee.saveToFile(saveFile);
                        System.out.println("Data saved to " + saveFile);
                    }
                        
                    case 14 -> {
                        System.out.print("Enter filename to load: ");
                        String loadFile = scanner.nextLine();
                        employee.loadFromFile(loadFile);
                    }
                        
                    case 15 -> {
                        System.out.println("Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }
                        
                    default -> System.out.println("Invalid choice! Please enter 1-15.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
