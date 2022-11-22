package com.example.social_bank.demo.user;

public class UserView {

    private String email;
    private String password;
    private String name;

    private String mobile_number;

    private int userId;

    private String username;


    private String creditCard;

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

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

    public UserView(String email, String name, int userId, String username, String mobile_number) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.username = username;
        this.mobile_number = mobile_number;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserView() {
    }

    public UserView(String email, String name) {
        this.email = email;
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

    public String getMobileNumber(){return mobile_number;}

    public void setMobileNumber(String mobile_number){this.mobile_number = mobile_number;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
