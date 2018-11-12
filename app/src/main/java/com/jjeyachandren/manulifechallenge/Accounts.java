package com.jjeyachandren.manulifechallenge;

public class Accounts {

    private String name;
    private String accountNumber;
    private String balance;

    public Accounts (String name, String accountNumber, String balance) {
        this.setName(name);
        this.setAccountNumber(accountNumber);
        this.setBalance(balance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


}
