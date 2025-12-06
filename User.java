package com.project.project;

public class User{
    int id;
    String name;
    String username;
    String password;
    Role role;
    
    public User(int id, String name, String username, String password, Role role){
    this.id = id;
    this.name = name;
    this.username = username;
    this.password = password;
    this.role = role;
    }
    public String toString(){
        return "ID: " + id + " | Name: " + name + " | Username: " + username + " | Role: " + role;
    }
    
    
}
