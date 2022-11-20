package com.example.social_bank.demo.account;

import javax.persistence.*;

@Entity
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column()
    private Double balance;

    @Column()
    private Double wallet;

    @Column()
    private Double savings_balance;

    @Column()
    private int user_id;

    public Accounts() {
    }

    public Accounts( Double balance, Double wallet, Double savingsBalance) {
        super();
        this.balance = balance;
        this.wallet = wallet;
        this.savings_balance = savingsBalance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public Double getSavingsBalance() {
        return savings_balance;
    }

    public void setSavingsBalance(Double savingsBalance) {
        this.savings_balance = savingsBalance;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }
}
