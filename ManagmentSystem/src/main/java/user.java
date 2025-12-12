/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mariam
 */
public class user {
    private int userID ; 
    private String userName ; 
    private String password ; 
    private String email ; 
    private String fullName ; 
    private String role ; 
    
    public user () {}
    public user(int u_ID , String u_Name ,  String pass , String mail , String f_Name , String r) {
      this.userID = u_ID ; 
      this.userName = u_Name ; 
      this.password = pass ; 
      this.email = mail ; 
      this.fullName = f_Name ; 
      this.role = r ; 
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
   
    
}
