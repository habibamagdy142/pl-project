package com.mycompany.final_plproject ; 
import java.io.* ; 
import java.util.* ; 
public class UserServices {
   private File fUser = new File("text files\\users.txt") ;
    
    public boolean login(String userName , String password) throws Exception {
     if(!fUser.exists()) {
      return false ; 
     }
     Scanner sFile = new Scanner(fUser) ;
      while(sFile.hasNextLine()) {
       String line = sFile.nextLine() ; 
       String[] data = line.split(",");
       if(data.length < 3) {
        continue;
       }
       String stored_userName = data[1] ; 
       String stored_password = data[2] ; 
       
       if(stored_userName.equalsIgnoreCase(userName) && stored_password.equals(password)) {
        sFile.close();
        System.out.println("Login Successfully");
        return true ; 
       }
      }
      sFile.close();      
      System.out.println("User Not Found!!");
      return false ;
    }
}

