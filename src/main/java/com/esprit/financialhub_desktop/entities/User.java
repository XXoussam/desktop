package com.esprit.financialhub_desktop.entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String url;
    private UserType userType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public User(int id, String email, String password, String url, UserType userType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.url = url;
        this.userType = userType;
    }
}