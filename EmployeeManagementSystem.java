/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author Mariam
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Main class to run the system
public class EmployeeManagementSystem {
    public static void main(String[] args) {
        System.out.println("=== Employee Management System ===");
        EmployeeApp app = new EmployeeApp();
        app.run();
    }
}

// Base User class
abstract class User {
    protected int id;
    protected String name;
    protected String role;
    protected String username;
    protected String password;
    
    public User(int id, String name, String role, String username, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getUsername() { return username; }
    
    // Authentication method
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Role: %s", id, name, role);
    }
}

// Employee class
class Employee extends User {
    private int hoursAttended;
    private List<AttendanceRecord> attendanceRecords;
    private List<VacationRequest> vacationRequests;
    private List<Penalty> penalties;
    private List<Task> assignedTasks;
    private LocalDateTime lastEntryTime;
    private boolean isClockedIn;
    
    public Employee(int id, String name, String username, String password) {
        super(id, name, "Employee", username, password);
        this.hoursAttended = 0;
        this.attendanceRecords = new ArrayList<>();
        this.vacationRequests = new ArrayList<>();
        this.penalties = new ArrayList<>();
        this.assignedTasks = new ArrayList<>();
        this.isClockedIn = false;
    }
    
    // Clock in with current time
    public void clockIn() {
        if (isClockedIn) {
            System.out.println("Already clocked in!");
            return;
        }
        lastEntryTime = LocalDateTime.now();
        isClockedIn = true;
        System.out.println(name + " clocked in at " + formatTime(lastEntryTime));
    }
    
    // Clock out with current time
    public void clockOut() {
        if (!isClockedIn) {
            System.out.println("Not clocked in!");
            return;
        }
        
        LocalDateTime exitTime = LocalDateTime.now();
        long hoursWorked = java.time.Duration.between(lastEntryTime, exitTime).toHours();
        hoursAttended += hoursWorked;
        
        AttendanceRecord record = new AttendanceRecord(lastEntryTime, exitTime, (int)hoursWorked);
        attendanceRecords.add(record);
        
        System.out.println(name + " clocked out at " + formatTime(exitTime));
        System.out.println("Hours worked this session: " + hoursWorked);
        System.out.println("Total hours attended: " + hoursAttended);
        
        isClockedIn = false;
        lastEntryTime = null;
    }
    
    // Manual clock in with specific time (for testing)
    public void manualClockIn(String timeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            lastEntryTime = LocalDateTime.parse(timeStr, formatter);
            isClockedIn = true;
            System.out.println(name + " manually clocked in at " + timeStr);
        } catch (Exception e) {
            System.out.println("Invalid time format. Use YYYY-MM-DD HH:mm");
        }
    }
    
    // Manual clock out with specific time (for testing)
    public void manualClockOut(String timeStr) {
        if (!isClockedIn) {
            System.out.println("Not clocked in!");
            return;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime exitTime = LocalDateTime.parse(timeStr, formatter);
            long hoursWorked = java.time.Duration.between(lastEntryTime, exitTime).toHours();
            hoursAttended += hoursWorked;
            
            AttendanceRecord record = new AttendanceRecord(lastEntryTime, exitTime, (int)hoursWorked);
            attendanceRecords.add(record);
            
            System.out.println(name + " manually clocked out at " + timeStr);
            System.out.println("Hours worked this session: " + hoursWorked);
            System.out.println("Total hours attended: " + hoursAttended);
            
            isClockedIn = false;
            lastEntryTime = null;
        } catch (Exception e) {
            System.out.println("Invalid time format. Use YYYY-MM-DD HH:mm");
        }
    }
    
    public void requestVacation(String startDate, String endDate, String reason) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate + "T09:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T17:00");
            VacationRequest request = new VacationRequest(start, end, reason, "Pending");
            vacationRequests.add(request);
            System.out.println("Vacation request submitted: " + request);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD");
        }
    }
    
    public void viewPenalties() {
        if (penalties.isEmpty()) {
            System.out.println("No penalties recorded.");
            return;
        }
        System.out.println("=== Penalties for " + name + " ===");
        penalties.forEach(System.out::println);
    }
    
    public void viewTasks() {
        if (assignedTasks.isEmpty()) {
            System.out.println("No tasks assigned.");
            return;
        }
        System.out.println("=== Tasks for " + name + " ===");
        for (int i = 0; i < assignedTasks.size(); i++) {
            Task task = assignedTasks.get(i);
            System.out.println((i + 1) + ". " + task);
        }
    }
    
    public void completeTask(int taskNumber) {
        if (taskNumber < 1 || taskNumber > assignedTasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }
        
        Task task = assignedTasks.get(taskNumber - 1);
        if (!task.isCompleted()) {
            task.markCompleted();
            System.out.println("Task completed: " + task.getDescription());
        } else {
            System.out.println("Task already completed.");
        }
    }
    
    public void addTask(String description, String dueDate) {
        try {
            LocalDateTime due = LocalDateTime.parse(dueDate + "T17:00");
            assignedTasks.add(new Task(description, due));
            System.out.println("Task added: " + description);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD");
        }
    }
    
    public void addPenalty(String reason, int severity) {
        if (severity < 1 || severity > 5) {
            System.out.println("Severity must be between 1 and 5.");
            return;
        }
        penalties.add(new Penalty(reason, LocalDateTime.now(), severity));
        System.out.println("Penalty added: " + reason);
    }
    
    public void viewAttendanceSummary() {
        System.out.println("=== Attendance Summary for " + name + " ===");
        System.out.println("Total Hours Attended: " + hoursAttended);
        System.out.println("Number of Records: " + attendanceRecords.size());
        System.out.println("Currently Clocked In: " + (isClockedIn ? "Yes" : "No"));
    }
    
    public void viewDetailedAttendance() {
        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records.");
            return;
        }
        System.out.println("=== Detailed Attendance Records ===");
        for (int i = 0; i < attendanceRecords.size(); i++) {
            System.out.println((i + 1) + ". " + attendanceRecords.get(i));
        }
    }
    
    public void viewVacationRequests() {
        if (vacationRequests.isEmpty()) {
            System.out.println("No vacation requests.");
            return;
        }
        System.out.println("=== Vacation Requests ===");
        for (int i = 0; i < vacationRequests.size(); i++) {
            System.out.println((i + 1) + ". " + vacationRequests.get(i));
        }
    }
    
    // Helper method
    private String formatTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

// Support classes
class AttendanceRecord {
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private int hoursWorked;
    
    public AttendanceRecord(LocalDateTime entryTime, LocalDateTime exitTime, int hoursWorked) {
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.hoursWorked = hoursWorked;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Entry: %s | Exit: %s | Hours: %d", 
            entryTime.format(formatter), exitTime.format(formatter), hoursWorked);
    }
}

class VacationRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reason;
    private String status;
    
    public VacationRequest(LocalDateTime startDate, LocalDateTime endDate, String reason, String status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Vacation: %s to %s | Reason: %s | Status: %s",
            startDate.format(formatter), endDate.format(formatter), reason, status);
    }
}

class Penalty {
    private String reason;
    private LocalDateTime date;
    private int severity;
    
    public Penalty(String reason, LocalDateTime date, int severity) {
        this.reason = reason;
        this.date = date;
        this.severity = severity;
    }
    
    @Override
    public String toString() {
        return String.format("Penalty: %s | Date: %s | Severity: %d/5",
            reason, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), severity);
    }
}

class Task {
    private String description;
    private LocalDateTime assignedDate;
    private LocalDateTime dueDate;
    private boolean completed;
    
    public Task(String description, LocalDateTime dueDate) {
        this.description = description;
        this.assignedDate = LocalDateTime.now();
        this.dueDate = dueDate;
        this.completed = false;
    }
    
    public void markCompleted() {
        this.completed = true;
    }
    
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String status = completed ? "✓ Completed" : "○ Pending";
        return String.format("%s | Due: %s | %s",
            description, dueDate.format(formatter), status);
    }
}

// Main application class
class EmployeeApp {
    private Scanner scanner;
    private Employee currentEmployee;
    
    public EmployeeApp() {
        scanner = new Scanner(System.in);
        currentEmployee = null;
    }
    
    public void run() {
        boolean systemRunning = true;
        
        while (systemRunning) {
            if (currentEmployee == null) {
                // Show login/register menu
                showMainMenu();
            } else {
                // Show employee menu
                showEmployeeMenu();
            }
        }
        scanner.close();
    }
    
    private void showMainMenu() {
        System.out.println("\n=== WELCOME ===");
        System.out.println("1. Login");
        System.out.println("2. Register New Employee");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> login();
            case 2 -> register();
            case 3 -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        // For demo purposes, create a default employee
        // In real system, this would check against a database
        if (username.equals("demo") && password.equals("demo123")) {
            currentEmployee = new Employee(1, "Demo Employee", username, password);
            System.out.println("Login successful! Welcome " + currentEmployee.getName());
        } else {
            System.out.println("Invalid credentials. Try username: demo, password: demo123");
        }
    }
    
    private void register() {
        System.out.println("\n=== REGISTER NEW EMPLOYEE ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Choose username: ");
        String username = scanner.nextLine();
        System.out.print("Choose password: ");
        String password = scanner.nextLine();
        
        // Generate a simple ID (in real system, this would come from database)
        int id = (int)(Math.random() * 1000) + 100;
        currentEmployee = new Employee(id, name, username, password);
        
        System.out.println("Registration successful!");
        System.out.println("Your Employee ID: " + id);
        System.out.println("Welcome to the system, " + name + "!");
    }
    
    private void showEmployeeMenu() {
        System.out.println("\n=== EMPLOYEE DASHBOARD ===");
        System.out.println("Logged in as: " + currentEmployee.getName());
        System.out.println("\n1. Clock In (Current Time)");
        System.out.println("2. Clock Out (Current Time)");
        System.out.println("3. Manual Clock In");
        System.out.println("4. Manual Clock Out");
        System.out.println("5. Request Vacation");
        System.out.println("6. View My Tasks");
        System.out.println("7. Complete a Task");
        System.out.println("8. Add a Task (Self-assign)");
        System.out.println("9. View My Penalties");
        System.out.println("10. Add a Penalty (Self-report)");
        System.out.println("11. View Attendance Summary");
        System.out.println("12. View Detailed Attendance");
        System.out.println("13. View Vacation Requests");
        System.out.println("14. Logout");
        System.out.print("Enter choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> currentEmployee.clockIn();
            case 2 -> currentEmployee.clockOut();
            case 3 -> {
                System.out.print("Enter entry time (YYYY-MM-DD HH:mm): ");
                String entryTime = scanner.nextLine();
                currentEmployee.manualClockIn(entryTime);
            }
            case 4 -> {
                System.out.print("Enter exit time (YYYY-MM-DD HH:mm): ");
                String exitTime = scanner.nextLine();
                currentEmployee.manualClockOut(exitTime);
            }
            case 5 -> {
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();
                System.out.print("Enter reason: ");
                String reason = scanner.nextLine();
                currentEmployee.requestVacation(startDate, endDate, reason);
            }
            case 6 -> currentEmployee.viewTasks();
            case 7 -> {
                currentEmployee.viewTasks();
                if (!currentEmployee.toString().contains("No tasks")) {
                    System.out.print("Enter task number to complete: ");
                    int taskNum = getIntInput();
                    currentEmployee.completeTask(taskNum);
                }
            }
            case 8 -> {
                System.out.print("Enter task description: ");
                String desc = scanner.nextLine();
                System.out.print("Enter due date (YYYY-MM-DD): ");
                String dueDate = scanner.nextLine();
                currentEmployee.addTask(desc, dueDate);
            }
            case 9 -> currentEmployee.viewPenalties();
            case 10 -> {
                System.out.print("Enter penalty reason: ");
                String reason = scanner.nextLine();
                System.out.print("Enter severity (1-5): ");
                int severity = getIntInput();
                currentEmployee.addPenalty(reason, severity);
            }
            case 11 -> currentEmployee.viewAttendanceSummary();
            case 12 -> currentEmployee.viewDetailedAttendance();
            case 13 -> currentEmployee.viewVacationRequests();
            case 14 -> {
                System.out.println("Logging out... Goodbye " + currentEmployee.getName() + "!");
                currentEmployee = null;
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private int getIntInput() {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}