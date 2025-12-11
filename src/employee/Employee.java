package employee;

import user.User;
import java.io.*;
import java.util.*;

public class Employee extends User implements WorkingHours, VacationHandler {

    public Employee(String id, String name, String username, String password) {
        super(id, name, "employee", username, password);
    }


    @Override
    public boolean login(String inputUser, String inputPass) {
        return username.equals(inputUser) && password.equals(inputPass);
    }


    @Override
    public void entryTime(String empId, String time) {
        try (FileWriter fw = new FileWriter("data/hours.txt", true)) {
            fw.write(empId + "," + time + ",ENTRY\n");
        } catch (Exception e) {
            System.out.println("Error writing entry time.");
        }
    }

    
    @Override
    public void exitTime(String empId, String time) {
        try (FileWriter fw = new FileWriter("data/hours.txt", true)) {
            fw.write(empId + "," + time + ",EXIT\n");
        } catch (Exception e) {
            System.out.println("Error writing exit time.");
        }
    }


    @Override
    public void requestVacation(String empId, String date, String reason) {
        try (FileWriter fw = new FileWriter("data/vacations.txt", true)) {
            fw.write(empId + "," + date + "," + reason + "\n");
        } catch (Exception e) {
            System.out.println("Error requesting vacation.");
        }
    }


    public List<String> viewPenalties() {
        List<String> penalties = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/penalties.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    penalties.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading penalties.");
        }
        return penalties;
    }


    public List<String> viewTasks() {
        List<String> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/tasks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    tasks.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading tasks.");
        }
        return tasks;
    }


    public void completeTask(String taskName) {
        try (FileWriter fw = new FileWriter("data/completedTasks.txt", true)) {
            fw.write(id + "," + taskName + "\n");
        } catch (Exception e) {
            System.out.println("Error writing completed task.");
        }
    }
}
