package com.esprit.financialhub_desktop.entities;

public class Admin extends User {
    public Admin(int id, String email, String password, String url, UserType userType) {
        super(id, email, password, url, userType);
    }
}