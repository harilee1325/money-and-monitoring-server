package com.sachin.money_monetary.demo.products;

import com.sachin.money_monetary.demo.util.JpaConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.ArrayList;
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



    public List<Purchase> getPurchase(int user_id) {
        List<Purchase> purchases =  (List<Purchase>) conn.getEntityManager()
                .createQuery("from Purchase").getResultList();


        List<Purchase> dummyP = new ArrayList<>();
        for (int i =0;i<purchases.size(); i++){
            if (Integer.parseInt(purchases.get(i).getUser_id()) == user_id){
               dummyP.add(purchases.get(i));
            }
        }
        return dummyP;
    }

    public Purchase getPurchase(String user_id) {
        List<Purchase> purchases =  (List<Purchase>) conn.getEntityManager()
                .createQuery("from Purchase").getResultList();

        for (int i =0;i<purchases.size(); i++){
            if ((purchases.get(i).getId()) == Integer.parseInt(user_id)){
                return purchases.get(i);
            }
        }
        return null;
    }

    public void updatePurchaseStatus(int id, String status) throws  Exception{

        try {
            EntityTransaction txn = conn.getEntityManager().getTransaction();
            txn.begin();
            conn.getEntityManager()
                    .createQuery("update Purchase set status=:status where id=:id")
                    .setParameter("status", status).setParameter("id", id).executeUpdate();
            txn.commit();
        }catch (NoResultException e){

            System.out.println("No result");
            return;
        }
    }

    public List<Purchase> getPurchase() {
        List<Purchase> purchases =  (List<Purchase>) conn.getEntityManager()
                .createQuery("from Purchase").getResultList();
        return purchases;
    }
}
