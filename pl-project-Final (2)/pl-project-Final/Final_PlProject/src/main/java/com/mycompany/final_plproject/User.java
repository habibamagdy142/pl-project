   package com.mycompany.final_plproject ;

import java.util.*;                           

public class User {
    private int userID ;          
    private String userName ; 
    private String password ; 
    private String email ; 
    private String fullName ; 
    private Role role ; 
    
    public User () {}
    public User(int u_ID , String u_Name ,  String pass , String mail , String f_Name , Role r) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", userName=" + userName + ", password=" + password + ", email=" + email + ", fullName=" + fullName + ", role=" + role + '}';
    }
    
}