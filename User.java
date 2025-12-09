package com.mycompany.mavenproject2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mariam
 */
public class User {
    protected int id;
    protected String name;
    protected String role; // em, tl, pm, admin

    public User(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public void login() {
        System.out.println(name + " logged in.");
    }

    public void changePassword(String newPass) {
        System.out.println("Password changed for " + name);
    }
    
}
