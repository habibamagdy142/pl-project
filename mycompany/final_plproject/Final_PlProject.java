/**package com.mycompany.final_plproject;
import java.io.* ; 
import java.util.* ; 
public class Final_PlProject {

    public static void main(String[] args) throws Exception {
       /** File f = new File("C:/Users/HP/OneDrive/Desktop/files/name.txt") ;
        
        System.out.println("Is File EXISTS ? " + f.exists());
        System.out.println("File length is : " + f.length() + "Bytes");
        System.out.println("File path : " + f.getAbsolutePath());
        **/ 
      
      /** File f2 = new File("C:/Users/HP/OneDrive/Desktop/files/grades.txt") ;
      
       if(f2.exists()) {
           System.out.println("File Already Exists!!");
           System.exit(0) ; 
       }
       
       PrintWriter output = new PrintWriter(f2) ;
      
        output.print("Ahmed");
        output.println(98);
        output.print("Mohammed");
        output.println(85);
       
        output.close();
       } **/ 
      /**
      File f3 = new File("C:/Users/HP/OneDrive/Desktop/files/scores.txt") ;
      
      Scanner input = new Scanner(f3) ;
      
      while(input.hasNext()) {
         String in = input.next();
         int score = input.nextInt() ;
          System.out.println("Name :  " + in  + " and his grade is : " + score);
      }
      input.close();
    **/
      
     /**  Report r = new Report (32 , 23 , 25 , "Message Sent Succesfully" , "20/11/2025") ;
        
      Services s = new Services();
      s.viewReport();
     /**
     Report r = new Report(30, 45, 20, "MESSAGE SENT", "15/11/2025")  ;
     Services s = new Services() ; 
     s.viewReport();  **/
   
 //  user u = new user(14 , "mohamed" , "ali@123" , "ali20@gmail.com" , "ali mohamed ali" , "projectmanager");
  // userServices us = new userServices(); 
  // us.addUser(u);
   // us.login("WALID", "walid123");
   
 /*  int employeeHours = 0;
                int teamLeaderHours = 0;
                int projectManagerHours = 0;
                int adminHours = 0;

                Scanner input = new Scanner(System.in);

                try {
                        System.out.print("Enter completed hours for Employee Module (out of 100): ");
                        employeeHours = Integer.parseInt(input.nextLine());
                        if (employeeHours < 0 || employeeHours > 100)
                                throw new IllegalArgumentException("Employee Module hours must be between 0 and 100.");

                        System.out.print("Enter completed hours for Team Leader Module (out of 60): ");
                        teamLeaderHours = Integer.parseInt(input.nextLine());
                        if (teamLeaderHours < 0 || teamLeaderHours > 60)
                                throw new IllegalArgumentException("Team Leader Module hours must be between 0 and 60.");

                        System.out.print("Enter completed hours for Project Manager Module (out of 40): ");
                        projectManagerHours = Integer.parseInt(input.nextLine());
                        if (projectManagerHours < 0 || projectManagerHours > 40)
                                throw new IllegalArgumentException("Project Manager Module hours must be between 0 and 40.");

                        System.out.print("Enter completed hours for Admin Module (out of 40): ");
                        adminHours = Integer.parseInt(input.nextLine());
                        if (adminHours < 0 || adminHours > 40)
                                throw new IllegalArgumentException("Admin Module hours must be between 0 and 40.");

                } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter numeric values only.");
                        return;

                } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        return;
                }

                Project proj = new Project("Our Project");

                Task t1 = new Task("Employee Module", 100);
                t1.setCompletedHours(employeeHours);

                Task t2 = new Task("Team leader Module", 60);
                t2.setCompletedHours(teamLeaderHours);

                Task t3 = new Task("Project manager Module", 40);
                t3.setCompletedHours(projectManagerHours);

                Task t4 = new Task("Admin Module", 40);
                t4.setCompletedHours(adminHours);

                proj.addTask(t1);
                proj.addTask(t2);
                proj.addTask(t3);
                proj.addTask(t4);

                ProjectManagerView.showProjectCompletion(proj);
        }   **/
/**}
} **/







/**
package com.mycompany.final_plproject;

import java.util.ArrayList;

public class Final_PlProject {

    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();

  
        AdminModule admin = new AdminModule(users);
        admin.adminMenu();
    }
}  **/  
 



 //* report class test ; 
package com.mycompany.final_plproject ;
 public class Final_PlProject {
     public static void main(String[] args) throws Exception {
      
//      Report r = new Report (37 , 32 , 23 , "M Sent to admin" , "22/11/2025") ;
//      Services s = new Services();
//     // s.sendReport(r) ; 
//     s.viewReport();
// Use invokeLater to ensure the GUI is created on the correct Thread
        java.awt.EventQueue.invokeLater(() -> {
//           vacationjframe vacationWindow = new vacationjframe();
//           vacationWindow.setVisible(true);
//           vacationWindow.setLocationRelativeTo(null);
           
            ViewPenaltiesJframe penaltiesWindow = new ViewPenaltiesJframe();
            penaltiesWindow.setVisible(true);
            penaltiesWindow.setLocationRelativeTo(null);
        });
}}
      

/*
// user class test
package com.mycompany.final_plproject ;
 public class Final_PlProject {
     public static void main(String[] args) throws Exception{
        
         
  UserServices us = new UserServices(); 
    us.login("Admin", "123");
     }
 }**/

/**
 * 
 test team leader 
package com.mycompany.final_plproject ;
 public class Final_PlProject {
    public static void main(String[] args) {
      TeamLeader tl = new TeamLeader();
      
   //   tl.addTeamMember();  
  /**   tl.deleteTeamMember(); **/ 
    //   tl.viewCompletedTasks() ;
    // tl.assignTask(202, "Task", "17-12-2025", 8);
//}
//}

/**
 * ProjectProgressService test;
package com.mycompany.final_plproject ;
 public class Final_PlProject {
    public static void main(String[] args) {
        
       System.out.println("=== PROJECT PROGRESS TEST ===");
        ProjectProgressService.updateCompletedHours(201, 8);
        ProjectProgressService.showProjectProgress();
    }
 } **/





/**
package com.mycompany.final_plproject;

public class Final_PlProject {

    public static void main(String[] args) throws Exception {

        // ⚠ لازم يكون ال ID ده موجود في users.txt
        Employee emp = new Employee(
                202,                // userId
                "emp1",             // userName
                "123",              // password
                "emp1@mail.com",
                "Ahmed Ali"
        );

        System.out.println("=== EMPLOYEE TEST START ===");

        emp.clockIn();
                Thread.sleep(65_000); 


        emp.viewTasks();

        Employee.updateCompletedHours(201, 5); 

        emp.completeTask(1);

        emp.clockOut();

    }
}
**/







/**


package com.mycompany.final_plproject;

public class Final_PlProject {

    public static void main(String[] args) {

        TeamLeader t = new TeamLeader();

        Employee e = new Employee(202,"omar","mohamed","ow@mail.com","omar waleed");


       System.out.println("=== ADD PENALTY TEST ===");
     // t.addPenalty(202, "mes");

        e.viewPenalties();
    }
}  
**/







/** 
 request vacation test 
package com.mycompany.final_plproject ;
public class Final_PlProject {
    public static void main(String[] args) {

        Employee emp = new Employee(202,"omar","mohamed","ow@mail.com","omar waleed") ;


        emp.requestVacation(
                "2025-06-01",
                "2025-06-05",
                "Medical reasons"
        );
    }
}
**/



/** respond to vacation test 
package com.mycompany.final_plproject ;
public class Final_PlProject{
    public static void main(String[] args) {

        TeamLeader leader = new TeamLeader();

        leader.respondToVacationRequest(
                202,          
                true,      
                "Approved by team leader"
        );
    }
}

*/

