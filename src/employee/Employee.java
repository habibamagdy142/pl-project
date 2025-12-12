package employee;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import user.User;

public class Employee extends User {
    private LocalDateTime clockInTime;
    private boolean isClockedIn;
    private int totalHours;

 
    private List<String> attendanceRecords;
    private List<String> tasks;
    private List<String> vacationRequests;
    private List<String> penalties;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Employee(String id, String name, String username, String password) {
        super(id, name, "Employee", username, password);
        this.isClockedIn = false;
        this.totalHours = 0;
        this.attendanceRecords = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.vacationRequests = new ArrayList<>();
        this.penalties = new ArrayList<>();
        loadProfile();
        loadAllLists();
    }

    @Override
    public boolean login(String inputUser, String inputPass) {
        return this.username.equals(inputUser) && this.password.equals(inputPass);
    }

    public String clockIn() {
        if (isClockedIn) return "You are already clocked in!";
        clockInTime = LocalDateTime.now();
        isClockedIn = true;
        saveProfile();
        return "Clocked in at: " + formatDateTime(clockInTime);
    }

    public String clockOut() {
        if (!isClockedIn) return "You are not clocked in!";
        LocalDateTime clockOutTime = LocalDateTime.now();
        long hoursWorked = Duration.between(clockInTime, clockOutTime).toHours();
        totalHours += hoursWorked;

        String record = formatDateTime(clockInTime) + " to " + formatDateTime(clockOutTime) + " - " + hoursWorked + " hours";
        appendLineToFile(getAttendanceFilename(), record);
        attendanceRecords.add(record);

        isClockedIn = false;
        clockInTime = null;
        saveProfile();
        return "Clocked out at: " + formatDateTime(clockOutTime) + "\nWorked: " + hoursWorked + " hours\nTotal hours: " + totalHours;
    }

    public String manualClockIn(String dateTimeStr) {
        if (isClockedIn) return "You are already clocked in!";
        try {
            LocalDateTime dt = LocalDateTime.parse(dateTimeStr, dtf);
            clockInTime = dt;
            isClockedIn = true;
            saveProfile();
            return "Manually clocked in at: " + dateTimeStr;
        } catch (Exception e) {
            return "Invalid format! Use: YYYY-MM-DD HH:mm";
        }
    }

    public String manualClockOut(String dateTimeStr) {
        if (!isClockedIn) return "You are not clocked in!";
        try {
            LocalDateTime dt = LocalDateTime.parse(dateTimeStr, dtf);
            if (dt.isBefore(clockInTime)) return "Clock out time must be after clock in time!";
            long hoursWorked = Duration.between(clockInTime, dt).toHours();
            totalHours += hoursWorked;
            String record = formatDateTime(clockInTime) + " to " + formatDateTime(dt) + " - " + hoursWorked + " hours";
            appendLineToFile(getAttendanceFilename(), record);
            attendanceRecords.add(record);

            isClockedIn = false;
            clockInTime = null;
            saveProfile();
            return "Manually clocked out at: " + dateTimeStr + "\nWorked: " + hoursWorked + " hours\nTotal hours: " + totalHours;
        } catch (Exception e) {
            return "Invalid format! Use: YYYY-MM-DD HH:mm";
        }
    }

    public String requestVacation(String startDate, String endDate, String reason) {
        String req = "From: " + startDate + " To: " + endDate + " Reason: " + reason + " Status: Pending";
        appendLineToFile(getVacationsFilename(), req);
        vacationRequests.add(req);
        return "Vacation requested from " + startDate + " to " + endDate;
    }

    public String viewTasks() {
        loadTasks(); // ensure fresh
        if (tasks.isEmpty()) return "No tasks assigned.";
        StringBuilder sb = new StringBuilder("Your Tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    public String addTask(String description) {
        String task = description + " [Pending]";
        appendLineToFile(getTasksFilename(), task);
        tasks.add(task);
        return "Task added: " + description;
    }

    public String completeTask(int taskNumber) {
        loadTasks();
        if (taskNumber < 1 || taskNumber > tasks.size()) return "Invalid task number!";
        String task = tasks.get(taskNumber - 1);
        if (task.contains("[Completed]")) return "Task already completed.";
        String completed = task.replace("[Pending]", "[Completed]");
        tasks.set(taskNumber - 1, completed);
        overwriteFile(getTasksFilename(), tasks);
        return "Task completed: " + completed;
    }

    public String viewPenalties() {
        loadPenalties();
        if (penalties.isEmpty()) return "No penalties recorded.";
        StringBuilder sb = new StringBuilder("Your Penalties:\n");
        for (int i = 0; i < penalties.size(); i++) {
            sb.append(i + 1).append(". ").append(penalties.get(i)).append("\n");
        }
        return sb.toString();
    }

    public String viewAttendanceSummary() {
        loadAllLists();
        return "Employee: " + name + " (ID: " + id + ")\n" +
               "Total Hours: " + totalHours + "\n" +
               "Attendance Records: " + attendanceRecords.size() + "\n" +
               "Currently Clocked In: " + (isClockedIn ? "Yes" : "No") + "\n" +
               "Tasks: " + tasks.size() + "\n" +
               "Vacation Requests: " + vacationRequests.size() + "\n" +
               "Penalties: " + penalties.size();
    }

    public String viewDetailedAttendance() {
        loadAttendance();
        if (attendanceRecords.isEmpty()) return "No attendance records.";
        StringBuilder sb = new StringBuilder("Attendance Records:\n");
        for (int i = 0; i < attendanceRecords.size(); i++) {
            sb.append("Record ").append(i + 1).append(": ").append(attendanceRecords.get(i)).append("\n");
        }
        return sb.toString();
    }

    public String viewVacationRequests() {
        loadVacations();
        if (vacationRequests.isEmpty()) return "No vacation requests.";
        StringBuilder sb = new StringBuilder("Vacation Requests:\n");
        for (int i = 0; i < vacationRequests.size(); i++) {
            sb.append("Request ").append(i + 1).append(": ").append(vacationRequests.get(i)).append("\n");
        }
        return sb.toString();
    }

    // ------------------ File handling helpers ------------------

    private String getProfileFilename() { return id + "_profile.txt"; }
    private String getAttendanceFilename() { return id + "_attendance.txt"; }
    private String getTasksFilename() { return id + "_tasks.txt"; }
    private String getVacationsFilename() { return id + "_vacations.txt"; }
    private String getPenaltiesFilename() { return id + "_penalties.txt"; }

    private void loadProfile() {
        File f = new File(getProfileFilename());
        if (!f.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("Total Hours:")) {
                    String val = line.substring(line.indexOf(':') + 1).trim();
                    try { totalHours = Integer.parseInt(val); } catch (NumberFormatException ignored) {}
                } else if (line.startsWith("Clocked In:")) {
                    String val = line.substring(line.indexOf(':') + 1).trim();
                    isClockedIn = val.equalsIgnoreCase("true");
                } else if (line.startsWith("Clock In Time:")) {
                    String val = line.substring(line.indexOf(':') + 1).trim();
                    try { clockInTime = LocalDateTime.parse(val, dtf); } catch (Exception ignored) {}
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading profile: " + e.getMessage());
        }
    }

    private void saveProfile() {
        List<String> lines = new ArrayList<>();
        lines.add("Employee ID: " + id);
        lines.add("Employee Name: " + name);
        lines.add("Total Hours: " + totalHours);
        lines.add("Clocked In: " + isClockedIn);
        if (isClockedIn && clockInTime != null) {
            lines.add("Clock In Time: " + formatDateTime(clockInTime));
        }
        overwriteFile(getProfileFilename(), lines);
    }

    private void loadAllLists() {
        loadAttendance(); loadTasks(); loadVacations(); loadPenalties();
    }

    private void loadAttendance() {
        attendanceRecords = readFileLines(getAttendanceFilename());
    }

    private void loadTasks() {
        tasks = readFileLines(getTasksFilename());
    }

    private void loadVacations() {
        vacationRequests = readFileLines(getVacationsFilename());
    }

    private void loadPenalties() {
        penalties = readFileLines(getPenaltiesFilename());
    }

    // Generic file helpers
    private List<String> readFileLines(String filename) {
        List<String> out = new ArrayList<>();
        File f = new File(filename);
        if (!f.exists()) return out;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = br.readLine()) != null) {
                if (!l.trim().isEmpty()) out.add(l);
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
        return out;
    }

    private void appendLineToFile(String filename, String line) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(line);
        } catch (IOException e) {
            System.out.println("Error appending to " + filename + ": " + e.getMessage());
        }
    }

    private void overwriteFile(String filename, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, false))) {
            for (String l : lines) pw.println(l);
        } catch (IOException e) {
            System.out.println("Error writing " + filename + ": " + e.getMessage());
        }
    }

    // Helper formatting
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(dtf);
    }

    private String getCurrentDate() {
        return LocalDateTime.now().format(df);
    }
}

