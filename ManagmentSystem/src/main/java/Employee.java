/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mariam
 */
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Employee class
class Employee extends user {
    private boolean isClockedIn;
    private String clockInTime;
    public File attendanceFile = new File("E:\\1\\attendance.txt");
    public File tasksFile = new File("E:\\1\\tasks.txt");
    public File vacationFile = new File("E:\\1\\vacation.txt");
    public File penaltiesFile = new File("E:\\1\\penalties.txt");
    
    public Employee(int userId, String userName, String password, String email, String fullName) {
        super(userId, userName, password, email, fullName, "Employee");
        this.isClockedIn = false;
        this.clockInTime = null;
    }
    
    // ===== 1. Clock In (Current Time) =====
    public void clockIn() {
        if (isClockedIn) {
            System.out.println("You are already clocked in!");
            return;
        }
        
        clockInTime = getCurrentDateTime();
        isClockedIn = true;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(attendanceFile, true))) {
            writer.println(getUserID() + "," + getUserName() + "," + clockInTime + ",IN");
            System.out.println("Clocked in at: " + clockInTime);
            System.out.println("✓ Attendance recorded in file");
        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }
    
    // ===== 2. Clock Out (Current Time) =====
    public void clockOut() {
        if (!isClockedIn) {
            System.out.println("You are not clocked in!");
            return;
        }
        
        String clockOutTime = getCurrentDateTime();
        isClockedIn = false;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(attendanceFile, true))) {
            writer.println(getUserID() + "," + getUserName() + "," + clockOutTime + ",OUT");
            System.out.println("Clocked out at: " + clockOutTime);
            
            // Calculate hours worked
            if (clockInTime != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime inTime = LocalDateTime.parse(clockInTime, formatter);
                LocalDateTime outTime = LocalDateTime.parse(clockOutTime, formatter);
                long hoursWorked = Duration.between(inTime, outTime).toHours();
                System.out.println("Worked: " + hoursWorked + " hours");
            }
            
            System.out.println("✓ Attendance recorded in file");
            clockInTime = null; // Reset clock in time
        } catch (IOException e) {
            System.out.println("Error saving attendance: " + e.getMessage());
        }
    }
    
    // ===== 3. Manual Clock In =====
    public void manualClockIn(String dateTimeStr) {
        if (isClockedIn) {
            System.out.println("You are already clocked in!");
            return;
        }
        
        try {
            // Validate date format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime.parse(dateTimeStr, formatter);
            
            clockInTime = dateTimeStr;
            isClockedIn = true;
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(attendanceFile, true))) {
                writer.println(getUserID() + "," + getUserName() + "," + clockInTime + ",IN");
                System.out.println("Manually clocked in at: " + clockInTime);
                System.out.println("✓ Attendance recorded in file");
            } catch (IOException e) {
                System.out.println("Error saving attendance: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Invalid format! Use: YYYY-MM-DD HH:mm");
        }
    }
    
    // ===== 4. Manual Clock Out =====
    public void manualClockOut(String dateTimeStr) {
        if (!isClockedIn) {
            System.out.println("You are not clocked in!");
            return;
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime outTime = LocalDateTime.parse(dateTimeStr, formatter);
            LocalDateTime inTime = LocalDateTime.parse(clockInTime, formatter);
            
            if (outTime.isBefore(inTime)) {
                System.out.println("Clock out time must be after clock in time!");
                return;
            }
            
            long hoursWorked = Duration.between(inTime, outTime).toHours();
            isClockedIn = false;
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(attendanceFile, true))) {
                writer.println(getUserID() + "," + getUserName() + "," + dateTimeStr + ",OUT");
                System.out.println("Manually clocked out at: " + dateTimeStr);
                System.out.println("Worked: " + hoursWorked + " hours");
                System.out.println("✓ Attendance recorded in file");
            } catch (IOException e) {
                System.out.println("Error saving attendance: " + e.getMessage());
            }
            
            clockInTime = null; // Reset clock in time
            
        } catch (Exception e) {
            System.out.println("Invalid format! Use: YYYY-MM-DD HH:mm");
        }
    }
    
    // ===== 5. Request Vacation =====
    public void requestVacation(String startDate, String endDate, String reason) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(vacationFile, true))) {
            writer.println(getUserID() + "," + getFullName() + "," + startDate + "," + endDate + "," + reason + ",Pending");
            System.out.println("Vacation requested from " + startDate + " to " + endDate);
            System.out.println("✓ Request saved to file");
        } catch (IOException e) {
            System.out.println("Error saving vacation request: " + e.getMessage());
        }
    }
    
    // ===== 6. View My Tasks =====
    public void viewTasks() {
        List<String> myTasks = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(tasksFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 2 && Integer.parseInt(data[1]) == getUserID()) {
                    myTasks.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tasks file not found!");
            return;
        }
        
        if (myTasks.isEmpty()) {
            System.out.println("No tasks assigned.");
        } else {
            System.out.println("Your Tasks:");
            for (int i = 0; i < myTasks.size(); i++) {
                String[] data = myTasks.get(i).split(",");
                System.out.println((i + 1) + ". ID: " + data[0] + ", Description: " + data[2] + 
                                 ", Status: " + data[3] + ", Deadline: " + data[4]);
            }
        }
    }
    
    // ===== 7. Complete a Task =====
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
        
        if (taskNumber < 1 || taskNumber > myTasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }
        
        String taskToComplete = myTasks.get(taskNumber - 1);
        String[] taskData = taskToComplete.split(",");
        
        // Update the task status
        String updatedTask = taskData[0] + "," + taskData[1] + "," + taskData[2] + ",Completed," + 
                           taskData[4] + "," + getCurrentDate();
        
        // Update in allTasks list
        for (int i = 0; i < allTasks.size(); i++) {
            if (allTasks.get(i).equals(taskToComplete)) {
                allTasks.set(i, updatedTask);
                break;
            }
        }
        
        // Write back to file
        try (PrintWriter writer = new PrintWriter(tasksFile)) {
            for (String task : allTasks) {
                writer.println(task);
            }
            System.out.println("Task completed: " + taskData[2]);
            System.out.println("✓ Task status updated in file");
        } catch (IOException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }
    
    // ===== 8. Add a Task (Self-assign) =====
    public void addTask(String description, String deadline) {
        try {
            // Generate task ID
            int newTaskId = generateTaskId();
            
            // Add to file
            try (PrintWriter writer = new PrintWriter(new FileWriter(tasksFile, true))) {
                writer.println(newTaskId + "," + getUserID() + "," + description + ",Pending," + deadline + ",-");
                System.out.println("Task added: " + description);
                System.out.println("Deadline: " + deadline);
                System.out.println("✓ Task saved to file");
            }
        } catch (IOException e) {
            System.out.println("Error saving task: " + e.getMessage());
        }
    }
    
    // ===== 9. View My Penalties =====
    public void viewPenalties() {
        List<String> myPenalties = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(penaltiesFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 2 && Integer.parseInt(data[1]) == getUserID()) {
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
    
    // ===== 10. View Attendance Summary =====
    public void viewAttendanceSummary() {
        System.out.println("=== Attendance Summary ===");
        System.out.println("Employee: " + getFullName() + " (ID: " + getUserID() + ")");
        System.out.println("Username: " + getUserName());
        System.out.println("Currently Clocked In: " + (isClockedIn ? "Yes" : "No"));
        
        if (isClockedIn) {
            System.out.println("Clock In Time: " + clockInTime);
        }
        
        // Count attendance records
        int attendanceCount = 0;
        try (Scanner scanner = new Scanner(attendanceFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 2 && Integer.parseInt(data[0]) == getUserID()) {
                    attendanceCount++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Attendance file not found!");
            return;
        }
        
        System.out.println("Total Attendance Records: " + (attendanceCount / 2)); // Each session has IN and OUT
    }
    
    // ===== 11. View Detailed Attendance =====
    public void viewDetailedAttendance() {
        System.out.println("=== Detailed Attendance ===");
        
        List<String> attendanceRecords = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(attendanceFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 2 && Integer.parseInt(data[0]) == getUserID()) {
                    attendanceRecords.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Attendance file not found!");
            return;
        }
        
        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records found.");
            return;
        }
        
        // Display pairs of IN/OUT
        for (int i = 0; i < attendanceRecords.size(); i += 2) {
            if (i + 1 < attendanceRecords.size()) {
                String[] inData = attendanceRecords.get(i).split(",");
                String[] outData = attendanceRecords.get(i + 1).split(",");
                
                System.out.println("Session " + ((i/2) + 1) + ":");
                System.out.println("  Clock In: " + inData[2]);
                System.out.println("  Clock Out: " + outData[2]);
                
                // Calculate hours
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime inTime = LocalDateTime.parse(inData[2], formatter);
                    LocalDateTime outTime = LocalDateTime.parse(outData[2], formatter);
                    long hours = Duration.between(inTime, outTime).toHours();
                    System.out.println("  Hours Worked: " + hours);
                } catch (Exception e) {
                    System.out.println("  Hours Worked: Could not calculate");
                }
                System.out.println();
            }
        }
    }
    
    // ===== 12. View Vacation Requests =====
    public void viewVacationRequests() {
        System.out.println("=== Vacation Requests ===");
        
        List<String> myRequests = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(vacationFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 2 && Integer.parseInt(data[0]) == getUserID()) {
                    myRequests.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vacation file not found!");
            return;
        }
        
        if (myRequests.isEmpty()) {
            System.out.println("No vacation requests.");
        } else {
            for (int i = 0; i < myRequests.size(); i++) {
                String[] data = myRequests.get(i).split(",");
                System.out.println((i + 1) + ". From: " + data[2] + " To: " + data[3] + 
                                 "\n   Reason: " + data[4] + "\n   Status: " + data[5]);
            }
        }
    }
    
    // ===== 13. Display Attendance File Contents =====
    public void displayAttendanceFile() {
        System.out.println("=== Attendance File Contents ===");
        displayFileContents(attendanceFile);
    }
    
    // ===== 14. Display Tasks File Contents =====
    public void displayTasksFile() {
        System.out.println("=== Tasks File Contents ===");
        displayFileContents(tasksFile);
    }
    
    // ===== 15. Display Vacation File Contents =====
    public void displayVacationFile() {
        System.out.println("=== Vacation File Contents ===");
        displayFileContents(vacationFile);
    }
    
    // ===== 16. Display Penalties File Contents =====
    public void displayPenaltiesFile() {
        System.out.println("=== Penalties File Contents ===");
        displayFileContents(penaltiesFile);
    }
    
    // ===== Helper Methods =====
    
    private int generateTaskId() {
        int lastId = 1000; // Starting ID
        
        if (!tasksFile.exists()) {
            return lastId + 1;
        }
        
        try (Scanner scanner = new Scanner(tasksFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] data = line.split(",");
                    if (data.length > 0) {
                        try {
                            int currentId = Integer.parseInt(data[0].trim());
                            if (currentId > lastId) {
                                lastId = currentId;
                            }
                        } catch (NumberFormatException e) {
                            // Skip invalid lines
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist, use default
        }
        
        return lastId + 1;
    }
    
    private String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.now().format(formatter);
    }
    
    private String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(formatter);
    }
    
    private void displayFileContents(File file) {
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getName());
            return;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            int lineCount = 0;
            System.out.println("Contents of " + file.getName() + ":");
            System.out.println("---------------------------------");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
                lineCount++;
            }
            System.out.println("---------------------------------");
            System.out.println("Total records: " + lineCount);
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}