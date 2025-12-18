package com.mycompany.final_plproject;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class AdminModule {

    private ArrayList<User> users;
    private Scanner input = new Scanner(System.in);
    private File usersFile = new File("text files\\users.txt");
    public AdminModule(){
    loadUsersFromFile();
    };

    public AdminModule(ArrayList<User> users) {
        this.users = users;
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        // التأكد من أن القائمة ليست null قبل البدء
        if (users == null) {
            users = new ArrayList<>();
        }
        
        users.clear();
        if (!usersFile.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length < 6) continue; // لتجنب الأخطاء في السطور الناقصة

                int id = Integer.parseInt(d[0].trim());
                String username = d[1].trim();
                String password = d[2].trim();
                String email = d[3].trim();
                String fullName = d[4].trim();
                Role role = Role.valueOf(d[5].trim().toUpperCase());

                users.add(new User(id, username, password, email, fullName, role));
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUsersToFile() {

        try (PrintWriter writer = new PrintWriter(new FileWriter(usersFile))) {

            for (User u : users) {
                writer.println(
                        u.getUserID() + "," +
                        u.getUserName() + "," +
                        u.getPassword() + "," +
                        u.getEmail() + "," +
                        u.getFullName() + "," +
                        u.getRole()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving users file.");
        }
    }

    public void viewAllUsers() {

        System.out.println("\n--- All Users ---");

        if (users.isEmpty()) {
            System.out.println("No Users Found.");
            return;
        }

        for (User u : users) {
            System.out.println(u);
        }

        System.out.println("----------------------");
    }
public void addUser(int id, String username, String password, String email, String fullName, String roleStr) {
    try {
        // تحويل النص إلى Enum Role
        Role role = Role.valueOf(roleStr.toUpperCase());
        
        // إضافة المستخدم للقائمة (التي يفترض أنها معرفة في الكلاس)
        users.add(new User(id, username, password, email, fullName, role));
        
        // حفظ التغييرات في الملف
        saveUsersToFile();
        
    } catch (IllegalArgumentException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Invalid Role! Please enter: EMPLOYEE, TEAM_LEADER, PROJECT_MANAGER, or ADMIN");
    }
}
//    public void addUser() {
//
//        System.out.print("Enter ID: ");
//        int id = input.nextInt();
//        input.nextLine();
//
//        System.out.print("Enter Username: ");
//        String username = input.nextLine();
//
//        System.out.print("Enter Password: ");
//        String password = input.nextLine();
//
//        System.out.print("Enter Email: ");
//        String email = input.nextLine();
//
//        System.out.print("Enter Full Name: ");
//        String fullName = input.nextLine();
//
//        System.out.println("Choose Role:");
//        System.out.println("1- EMPLOYEE");
//        System.out.println("2- TEAM_LEADER");
//        System.out.println("3- PROJECT_MANAGER");
//        System.out.println("4- ADMIN");
//
//        int r = input.nextInt();
//        Role role = Role.values()[r - 1];
//
//        users.add(new User(id, username, password, email, fullName, role));
//        saveUsersToFile();
//
//        System.out.println("User Added Successfully");
//    }

    public void deleteUser() {

        System.out.print("Enter User ID To Delete: ");
        int id = input.nextInt();

        User found = null;

        for (User u : users) {
            if (u.getUserID() == id) {
                found = u;
                break;
            }
        }

        if (found == null) {
            System.out.println("User Not Found!");
            return;
        }

        users.remove(found);
        saveUsersToFile();

        System.out.println("User Deleted Successfully");
    }

    public void updateUser() {

        System.out.print("Enter User ID To Update: ");
        int id = input.nextInt();
        input.nextLine();

        User found = null;

        for (User u : users) {
            if (u.getUserID() == id) {
                found = u;
                break;
            }
        }

        if (found == null) {
            System.out.println("User Not Found!");
            return;
        }

        System.out.print("Enter New Full Name: ");
        found.setFullName(input.nextLine());

        System.out.print("Enter New Username: ");
        found.setUserName(input.nextLine());

        System.out.print("Enter New Password: ");
        found.setPassword(input.nextLine());

        System.out.print("Enter New Email: ");
        found.setEmail(input.nextLine());

        System.out.println("Choose New Role:");
        System.out.println("1- EMPLOYEE");
        System.out.println("2- TEAM_LEADER");
        System.out.println("3- PROJECT_MANAGER");
        System.out.println("4- ADMIN");

        int r = input.nextInt();
        found.setRole(Role.values()[r - 1]);

        saveUsersToFile();
        System.out.println("User Updated Successfully");
    }

    public void searchUser() {

        input.nextLine();
        System.out.print("Enter User ID or Username: ");
        String search = input.nextLine();

        for (User u : users) {
            if (String.valueOf(u.getUserID()).equals(search)
                    || u.getUserName().equalsIgnoreCase(search)) {

                System.out.println("User Found:");
                System.out.println(u);
                return;
            }
        }

        System.out.println("User Not Found!");
    }

//    public void adminMenu() {
//
//        int choice;
//
//        do {
//            System.out.println("\n--- ADMIN MENU ---");
//            System.out.println("1- View All Users");
//            System.out.println("2- Add User");
//            System.out.println("3- Update User");
//            System.out.println("4- Delete User");
//            System.out.println("5- Search User");
//            System.out.println("6- Exit");
//            System.out.print("Enter Choice: ");
//
//            choice = input.nextInt();
//
//            switch (choice) {
//                case 1 : viewAllUsers();
//                break ; 
//                case 2 : addUser();
//                break  ;
//                case 3 : updateUser();
//                break ; 
//                case 4 : deleteUser();
//                break ; 
//                case 5 : searchUser();
//                break ; 
//                case 6 : System.out.println("Exiting Admin Module...");
//                break ; 
//                default : System.out.println("Invalid Choice!");
//            }
//
//        } while (choice != 6);
//    }
}
