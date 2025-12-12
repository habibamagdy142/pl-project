
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mariam
 */
// Test main class
public class EmployeeTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== EMPLOYEE MANAGEMENT SYSTEM ===\n");
        
        // Create employee using your user class constructor
        Employee emp = new Employee(101, "johndoe", "pass123", "john@company.com", "John Doe");
        
        System.out.println("Welcome, " + emp.getFullName() + " (ID: " + emp.getUserID() + ")");
        System.out.println("Username: " + emp.getUserName());
        System.out.println("Role: " + emp.getRole());
        System.out.println("\nFiles will be saved to: E:\\1\\");
        System.out.println("  - attendance.txt\n  - tasks.txt\n  - vacation.txt\n  - penalties.txt\n");
        
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
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
            System.out.println("13. Display Attendance File");
            System.out.println("14. Display Tasks File");
            System.out.println("15. Display Vacation File");
            System.out.println("16. Display Penalties File");
            System.out.println("17. Exit");
            System.out.print("Enter your choice (1-17): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                System.out.println();
                
                switch (choice) {
                    case 1 -> emp.clockIn();
                    case 2 -> emp.clockOut();
                    case 3 -> {
                        System.out.print("Enter date and time (YYYY-MM-DD HH:mm): ");
                        String clockInTime = scanner.nextLine();
                        emp.manualClockIn(clockInTime);
                    }
                    case 4 -> {
                        System.out.print("Enter date and time (YYYY-MM-DD HH:mm): ");
                        String clockOutTime = scanner.nextLine();
                        emp.manualClockOut(clockOutTime);
                    }
                    case 5 -> {
                        System.out.print("Enter start date (YYYY-MM-DD): ");
                        String startDate = scanner.nextLine();
                        System.out.print("Enter end date (YYYY-MM-DD): ");
                        String endDate = scanner.nextLine();
                        System.out.print("Enter reason: ");
                        String reason = scanner.nextLine();
                        emp.requestVacation(startDate, endDate, reason);
                    }
                    case 6 -> emp.viewTasks();
                    case 7 -> {
                        System.out.print("Enter task number to complete: ");
                        int taskNum = Integer.parseInt(scanner.nextLine());
                        emp.completeTask(taskNum);
                    }
                    case 8 -> {
                        System.out.print("Enter task description: ");
                        String taskDesc = scanner.nextLine();
                        System.out.print("Enter deadline (YYYY-MM-DD): ");
                        String deadline = scanner.nextLine();
                        emp.addTask(taskDesc, deadline);
                    }
                    case 9 -> emp.viewPenalties();
                    case 10 -> emp.viewAttendanceSummary();
                    case 11 -> emp.viewDetailedAttendance();
                    case 12 -> emp.viewVacationRequests();
                    case 13 -> emp.displayAttendanceFile();
                    case 14 -> emp.displayTasksFile();
                    case 15 -> emp.displayVacationFile();
                    case 16 -> emp.displayPenaltiesFile();
                    case 17 -> {
                        System.out.println("Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-17.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}