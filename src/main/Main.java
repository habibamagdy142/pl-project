package main;

import java.util.Scanner;

import employee.Employee;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
     Employee employee = new Employee("2024", "lama", "lola", "password123");

        System.out.println("=== Employee Management System ===");
        System.out.println("Logged in as: " + employee.getName() + " (ID: " + employee.getId() + ")");

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
            System.out.println("13. Save Profile (force save)");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> System.out.println(employee.clockIn());
                    case 2 -> System.out.println(employee.clockOut());
                    case 3 -> {
                        System.out.print("Enter date and time (YYYY-MM-DD HH:mm): ");
                        String t = scanner.nextLine();
                        System.out.println(employee.manualClockIn(t));
                    }
                    case 4 -> {
                        System.out.print("Enter date and time (YYYY-MM-DD HH:mm): ");
                        String t = scanner.nextLine();
                        System.out.println(employee.manualClockOut(t));
                    }
                    case 5 -> {
                        System.out.print("Enter start date (YYYY-MM-DD): ");
                        String s = scanner.nextLine();
                        System.out.print("Enter end date (YYYY-MM-DD): ");
                        String e = scanner.nextLine();
                        System.out.print("Enter reason: ");
                        String r = scanner.nextLine();
                        System.out.println(employee.requestVacation(s, e, r));
                    }
                    case 6 -> System.out.println(employee.viewTasks());
                    case 7 -> {
                        System.out.println(employee.viewTasks());
                        System.out.print("Enter task number to complete: ");
                        int tn = Integer.parseInt(scanner.nextLine());
                        System.out.println(employee.completeTask(tn));
                    }
                    case 8 -> {
                        System.out.print("Enter task description: ");
                        String desc = scanner.nextLine();
                        System.out.println(employee.addTask(desc));
                    }
                    case 9 -> System.out.println(employee.viewPenalties());
                    case 10 -> System.out.println(employee.viewAttendanceSummary());
                    case 11 -> System.out.println(employee.viewDetailedAttendance());
                    case 12 -> System.out.println(employee.viewVacationRequests());
                    case 13 -> {
                        System.out.println("Profile saved.");
       }
                    case 14 -> {
                        System.out.println("Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice! Please enter 1-14.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid number!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

