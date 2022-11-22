package com.example.social_bank.demo.account;

import javax.persistence.*;

@Entity
public class Savings_Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    private Double savings_balance;

    @Column()
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Double getSavings_balance() {
        return savings_balance;
    }

    public void setSavings_balance(Double savings_balance) {
        this.savings_balance = savings_balance;
    }

    public Savings_Account() {
    }
}
