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

    @Column()
    private int debit_card_number;

    public Accounts() {

    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Accounts(Double balance, Double wallet, Double savings_balance, int user_id, int debit_card_number) {
        super();
        this.balance = balance;
        this.wallet = wallet;
        this.savings_balance = savings_balance;
        this.user_id = user_id;
        this.debit_card_number = debit_card_number;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public Double getSavings_balance() {
        return savings_balance;
    }

    public void setSavings_balance(Double savings_balance) {
        this.savings_balance = savings_balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDebit_card_number() {
        return debit_card_number;
    }

    public void setDebit_card_number(int debit_card_number) {
        this.debit_card_number = debit_card_number;
    }
}
