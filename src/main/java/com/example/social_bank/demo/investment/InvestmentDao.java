package com.example.social_bank.demo.investment;

import com.example.social_bank.demo.util.JpaConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

@Component("investmentDao")
public class InvestmentDao {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JpaConn conn;

    public List<Investment_Types> getInvestmentTypes() {
        List<Investment_Types> investmentTypes = (List<Investment_Types>) conn.getEntityManager().createQuery("from Investment_Types").getResultList();
    return investmentTypes;
    }


    public String createInvestment(Investment_Details e) {
        EntityTransaction txn = conn.getEntityManager().getTransaction();
        txn.begin();
        conn.getEntityManager().persist(e);
        txn.commit();
        return "done";
    }

    public List<Investment_Details> getInvestments(int userId) {
        List<Investment_Details> investmentTypes = (List<Investment_Details>) conn.getEntityManager().createQuery("from Investment_Details").getResultList();

        List<Investment_Details>  investmentDetails = new ArrayList<>();
        for (int i=0;i<investmentTypes.size();i++){
            if (investmentTypes.get(i).getUser_id().contains(String.valueOf(userId))){
                investmentDetails.add(investmentTypes.get(i));
            }
        }
        return investmentDetails;
    }
}
