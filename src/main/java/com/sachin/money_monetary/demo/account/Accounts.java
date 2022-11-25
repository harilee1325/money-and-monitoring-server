package com.sachin.money_monetary.demo.account;

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
    private int user_id;

    @Column()
    private String debit_card_number;


    public Accounts() {

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCredit_card_number() {
        return debit_card_number;
    }

    public void setCredit_card_number(String debit_card_number) {
        this.debit_card_number = debit_card_number;
    }
}
