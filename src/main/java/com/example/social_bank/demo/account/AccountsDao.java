package com.example.social_bank.demo.account;

import com.example.social_bank.demo.user.Users;
import com.example.social_bank.demo.util.JpaConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.transaction.Transaction;
import java.util.List;

@Component("accountDao")

public class AccountsDao {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JpaConn conn;

    public String createAccount(Accounts e) {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        conn.getEntityManager().persist(e);
        txn.commit();
        return "done";
    }

    public String createSavings(Savings_Account e) {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        conn.getEntityManager().persist(e);
        txn.commit();
        return "done";
    }

    public Accounts getAccount(int user_id) {

        try {
            List<Accounts> accounts = (List<Accounts>) conn.getEntityManager()
                    .createQuery("from Accounts where").getResultList();
            for (int i=0;i<accounts.size();i++){
                if (accounts.get(i).getUser_id() == user_id){
                    return accounts.get(i);
                }
            }
            return null;
        }catch (NoResultException noResultException){
            return null;
        }

    }

    public Accounts getAccountWithDebitCard(String cardNo) {

        try {
            Accounts accounts = (Accounts) conn.getEntityManager()
                    .createQuery("from Accounts where credit_card_number=:credit_card_number")
                    .setParameter("credit_card_number", cardNo).getSingleResult();
            return accounts;
        }catch (NoResultException noResultException){
            return null;
        }

    }

    public  Savings_Account getSavingsAccount(int user_id) {

        try {
            List<Savings_Account> accounts = ( List<Savings_Account>) conn.getEntityManager()
                    .createQuery("from Savings_Account").getResultList();

            for (int i=0;i<accounts.size();i++){
                if (accounts.get(i).getUser_id() == user_id){
                    return accounts.get(i);
                }
            }
            return null;

        }catch (NoResultException noResultException){
            return null;
        }

    }

    public void updateAccount(Long id, Accounts e) throws Exception {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        e.setId(id);
        conn.getEntityManager().merge(e);
        conn.getEntityManager().flush();
        txn.commit();
    }

    public void updateSavingsAccount(Long id, Savings_Account e) throws Exception {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        e.setId(id);
        conn.getEntityManager().merge(e);
        conn.getEntityManager().flush();
        txn.commit();
    }

    public void updateUserId(int userId, String creditCard) throws  Exception{

        try {
            EntityTransaction txn = conn.getEntityManager().getTransaction();
            txn.begin();
            conn.getEntityManager()
                    .createQuery("update Accounts set user_id=:user_id where credit_card_number=:credit_card_number")
                    .setParameter("user_id", userId).setParameter("credit_card_number", creditCard).executeUpdate();
            txn.commit();
        }catch (NoResultException e){

            System.out.println("No result");
            return;
        }
    }
}
