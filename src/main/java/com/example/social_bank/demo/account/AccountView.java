package com.example.social_bank.demo.account;

public class AccountView {
    private String balance;

    private String wallet;

    private String savings;

    private String userId;

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    private String creditCard;

    public AccountView(String balance, String wallet, String savings, String userId, String creditCard) {
        this.balance = balance;
        this.wallet = wallet;
        this.savings = savings;
        this.userId = userId;
        this.creditCard = creditCard;
    }

    public AccountView() {
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
