package com.sachin.money_monetary.demo.products;

import javax.persistence.*;

@Entity
public class Purchase {
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
    private String product_id;

    @Column
    private String user_id;


    @Column
    private String date;

    @Column
    private String payment_type;

    @Column
    private String product_price;

    @Column
    private String savings_amount;

    @Column
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Purchase(String product_id, String user_id, String date, String payment_type, String product_price, String savings_amount) {
       super();
        this.product_id = product_id;
        this.user_id = user_id;
        this.date = date;
        this.payment_type = payment_type;
        this.product_price = product_price;
        this.savings_amount = savings_amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getSavings_amount() {
        return savings_amount;
    }

    public void setSavings_amount(String savings_amount) {
        this.savings_amount = savings_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Purchase() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
