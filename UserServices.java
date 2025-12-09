package com.mycompany.pl2_project;
import java.io.* ; 
import java.util.* ; 
public class UserServices {
   private File fUser = new File("C:/Users/HP/OneDrive/Desktop/Pl Porject/text files/users.txt") ;
    public void addUser(user u) throws Exception {
       if (!fUser.exists()) {
        fUser.createNewFile() ; 
       }
       
       
       FileWriter fwUser = new FileWriter(fUser , true) ;
       
       fwUser.append(u.getUserID()+ " , " + u.getUserName() + " , " + u.getPassword() + " , " + u.getEmail() + " , " + u.getFullName() + " , " + u.getRole() + System.lineSeparator());
       
       fwUser.close();
       
        System.out.println("User added successfully.");
    }
    
    public boolean login(String userName , String password) throws Exception {
     if(!fUser.exists()) {
      return false ; 
     }
     Scanner sFile = new Scanner(fUser) ;
      while(sFile.hasNextLine()) {
       String line = sFile.nextLine() ; 
       String[] data = line.split(" , ");
       if(data.length < 3) {
        continue;
       }
       String stored_userName = data[1] ; 
       String stored_password = data[2] ; 
       
       if(stored_userName.equals(userName) && stored_password.equals(password)) {
        sFile.close();
        return true ; 
       }
      }
      sFile.close();
      return false ; 
    }
}
