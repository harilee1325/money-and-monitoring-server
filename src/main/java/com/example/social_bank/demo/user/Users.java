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

    @Column
    private String mobile_number;

    @Column
    private String username;

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users() {
    }

    public Users(String name, String email, String password, String mobile_number) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile_number = mobile_number;
    }

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobileNumber(String mobile_number) {
        return mobile_number;
    }

    public void setMobileNumber(String mobile_number) {
        this.mobile_number = mobile_number;
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
