package com.example.social_bank.demo.investment;

import com.example.social_bank.demo.account.Accounts;
import com.example.social_bank.demo.products.Products;
import com.example.social_bank.demo.util.JpaConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityTransaction;
import java.util.List;

@Component("investmentDao")
public class InvestmentDao {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JpaConn conn;

    public List<InvestmentTypes> getInvestmentTypes() {
        return (List<InvestmentTypes>) conn.getEntityManager().createQuery("from InvestmentTypes").getResultList();
    }


    public String createInvestment(InvestmentDetails e) {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        conn.getEntityManager().persist(e);
        txn.commit();
        return "done";
    }

}
