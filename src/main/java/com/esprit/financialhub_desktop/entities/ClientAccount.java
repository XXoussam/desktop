package com.esprit.financialhub_desktop.entities;

public class ClientAccount {
    private String account_number;
    private String type;
    private double balance;
    private String currency;
    private String createdAt;

    public ClientAccount(String account_number, String type, double balance, String currency, String createdAt) {
        this.account_number = account_number;
        this.type = type;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public ClientAccount() {
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

