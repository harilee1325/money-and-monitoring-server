package com.example.social_bank.demo.products;

import com.example.social_bank.demo.account.Accounts;
import com.example.social_bank.demo.util.JpaConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

@Component("productDao")

public class ProductDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JpaConn conn;

    public String createProduct(Products p) {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        conn.getEntityManager().persist(p);
        txn.commit();
        return "done";
    }

    public List<Products> getAllEmployees() {
        return (List<Products>) conn.getEntityManager().createQuery("from Products").getResultList();
    }


    public String makePurchase(Purchase purchase) {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        conn.getEntityManager().persist(purchase);
        txn.commit();
        return "done";

    }

    public Products getProducts(int id) {

        try {
            Products products = (Products) conn.getEntityManager()
                    .createQuery("from Products where id=:id")
                    .setParameter("id", id).getSingleResult();
            return products;
        }catch (NoResultException noResultException){
            return null;
        }
    }

    public List<Purchase> getPurchase() {
        return (List<Purchase>) conn.getEntityManager().createQuery("from Purchase").getResultList();

    }
}
