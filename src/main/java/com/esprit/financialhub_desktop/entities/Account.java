package com.esprit.financialhub_desktop.entities;

public class Account {
    private int id;
    private String account_number;
    private double balance;
    private String currency;

    public Account(int id, String account_number, double balance, String currency) {
        this.id = id;
        this.account_number = account_number;
        this.balance = balance;
        this.currency = currency;
    }

    public Account() {

    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

