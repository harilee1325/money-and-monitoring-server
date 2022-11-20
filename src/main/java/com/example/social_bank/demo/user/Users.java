package com.example.social_bank.demo.user;

import javax.persistence.*;

@javax.persistence.Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column()
    private String name;
    @Column(unique = true)
    private String email;

    public Users() {
    }

    public Users(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
