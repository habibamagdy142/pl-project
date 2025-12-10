package com.project.project;
import java.util.ArrayList;
import java.util.Scanner;
/*_____________________________________________________________________
                             
_______________________________________________________________________
*/
public class AdminModule {
    private ArrayList<User> users;
    private Scanner input = new Scanner(System.in);
    
    public AdminModule(ArrayList<User> users){
     this.users = users;   
    }
    
    /*           VIEW USERS      */
    
    public void viewAllUsers(){
        System.out.println("\n---All Users---");
        
        if(users.isEmpty())
            System.out.println("No Users Found.");
        else{
            for(User u : users){
                System.out.println(u);
            }
        }
        System.out.println("--------------------------------");
    }
    
    /*          ADD USER         */
        public void addUser() {
        System.out.print("Enter ID: ");
        int id = input.nextInt();  
        input.nextLine();  

        System.out.println("Enter Name: ");
        String name = input.nextLine();

        System.out.println("Enter Username: ");
        String username = input.nextLine();

        System.out.println("Enter Password: ");
        String password = input.nextLine();

        System.out.println("Enter Email: ");
        String email = input.nextLine();

        System.out.println("Choose Role:");
        System.out.println("1- EMPLOYEE");
        System.out.println("2- TEAM_LEADER");
        System.out.println("3- PROJECT_MANAGER");
        System.out.println("4- ADMIN");

        int r = input.nextInt();
        Role role = Role.values()[r - 1];

        User newUser = new User(id, name, username, password, role);
        users.add(newUser);

        System.out.println("User Added Successfully!\n");
    }

    
    /*                DELETE USER             */
    
    public void deleteUser(){
        System.out.println("Enter User ID To Delete: ");
        int id = input.nextInt();
        
        User found = null;
        
        for(User u : users){
            if(u.id == id){
                found = u;
                break;
            }
        }
        if(found != null){
            users.remove(found);
            System.out.println("User Deleted.");
        }else{
            System.out.println("User Not Found!.");
        }
    }
    
    /*             Update User          */
    
    public void updateUser(){
        System.out.println("Enter User ID to Update: ");
        int id = input.nextInt();
         User found = null;
         
         
         for(User u : users){
             if(u.id == id){
                 found = u;
                 break;
             }
         }
         if(found == null){
             System.out.println("User Not Found!");
             return;
         }
         System.out.println("Enter New Name: ");
         found.name = input.nextLine();
         
         System.out.println("Enter New Username: ");
         found.username = input.nextLine();
         
         System.out.println("Enter New Password: ");
         found.password = input.nextLine();

         System.out.println(Enter New Email: ");
         found.email = input.nextLine();
         
         System.out.println("Choose New Role: ");
         System.out.println("1- EMPLOYEE");
         System.out.println("2- TEAM_LEADER");
         System.out.println("3- PROJECT_MANAGER");
         System.out.println("4- ADMIN");
         
         int r = input.nextInt();
         found.role = Role.values()[r-1];
         
         System.out.println("User Updated Successfully.\n");
    }
    /*         SEARCH USER (ID OR USERNAME)    */
      public void searchUser() {
        System.out.println("Enter User ID or Username to Search: ");
        input.nextLine();
        String search = input.nextLine();

        boolean found = false;

        for (User u : users) {
            if (String.valueOf(u.id).equals(search) || u.username.equalsIgnoreCase(search)) {
                System.out.print("\nUser Found:" + " " + u);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("\nUser Not Found.");
        }
    }

    /*         ADMIN MENU           */
    
      public void adminMenu(){
        int choice;
        
        do{
            System.out.println("\n---ADMIN MENU---");
            System.out.println("1- View All Users");
            System.out.println("2- Add User");
            System.out.println("3- Update User");
            System.out.println("4- Delete User");
            System.Out.Println("5- Search User");
            System.out.println("6- Exit");
            System.out.println("Enter Choice: ");
            choice = input.nextInt();
            
            switch(choice){
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    searchUser();
                    break;
              case 6:
                    System.out.println("Exiting Admin Module.");
                    break;
                default:
                    System.out.println("Invalid Choice!");                 
            }
        }while(choice != 5);
    }  
}



