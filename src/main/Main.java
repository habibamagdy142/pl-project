package main;

import employee.Employee;

public class Main {
    public static void main(String[] args) {

        Employee e = new Employee("101", "Lama", "lamaUser", "1234");

        System.out.println("Login: " + e.login("lamaUser", "1234"));


        e.entryTime("101", "08:00");
        e.exitTime("101", "17:00");
        e.requestVacation("101", "2025-02-10", "Family trip");
        System.out.println(e.viewTasks());
        e.completeTask("Design UI");
    }
}
