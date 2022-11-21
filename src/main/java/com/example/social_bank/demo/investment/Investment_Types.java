package com.example.social_bank.demo.investment;


import javax.persistence.*;

@Entity
public class Investment_Types {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;


    @Column
    private String investment_type;

    @Column
    private String investment_name;

    @Column
    private String investment_percent;

    @Column
    private String investment_desc;

    public String getInvestment_desc() {
        return investment_desc;
    }

    public void setInvestment_desc(String investment_desc) {
        this.investment_desc = investment_desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvestment_type() {
        return investment_type;
    }

    public void setInvestment_type(String investment_type) {
        this.investment_type = investment_type;
    }

    public String getInvestment_name() {
        return investment_name;
    }

    public void setInvestment_name(String investment_name) {
        this.investment_name = investment_name;
    }

    public String getInvestment_percent() {
        return investment_percent;
    }

    public void setInvestment_percent(String investment_percent) {
        this.investment_percent = investment_percent;
    }
}
