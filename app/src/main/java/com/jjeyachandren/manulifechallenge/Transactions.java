package com.jjeyachandren.manulifechallenge;

public class Transactions {

    private String date;
    private String description;
    private String deposit_amount;
    private String withdrawl_amount;
    private String balance;
    private String transaction_uid;

    public Transactions(String date, String description, String deposit_amount, String withdrawl_amount, String balance, String transaction_uid) {

            this.setDate(date);
            this.setDescription(description);
            this.setDeposit_amount(deposit_amount);
            this.setWithdrawl_amount(withdrawl_amount);
            this.setBalance(balance);
            this.setTransaction_uid(transaction_uid);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeposit_amount() {
        return deposit_amount;
    }

    public void setDeposit_amount(String deposit_amount) {
        this.deposit_amount = deposit_amount;
    }

    public String getWithdrawl_amount() {
        return withdrawl_amount;
    }

    public void setWithdrawl_amount(String withdrawl_amount) {
        this.withdrawl_amount = withdrawl_amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTransaction_uid() {
        return transaction_uid;
    }

    public void setTransaction_uid(String transaction_uid) {
        this.transaction_uid = transaction_uid;
    }


}
