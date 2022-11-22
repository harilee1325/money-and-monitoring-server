package com.example.social_bank.demo;

import com.example.social_bank.demo.account.Accounts;
import com.example.social_bank.demo.account.AccountsDao;
import com.example.social_bank.demo.account.Savings_Account;
import com.example.social_bank.demo.investment.InvestmentDao;
import com.example.social_bank.demo.investment.Investment_Details;
import com.example.social_bank.demo.investment.Investment_Types;
import com.example.social_bank.demo.products.ProductDao;
import com.example.social_bank.demo.products.Products;
import com.example.social_bank.demo.products.Purchase;
import com.example.social_bank.demo.user.UserDao;
import com.example.social_bank.demo.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Services {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("bCryptEncoder")
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserDao dao;

    @Autowired
    AccountsDao accountsDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    InvestmentDao investmentDao;
    
    public boolean createEmployee(Users e) {
        try {
            if (!validateEmployee(e)) {
                return false;
            }
            dao.createAdmin(e);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    private boolean validateEmployee(Users e) {

        // Checks if password is good, if admin check they are L3 or L4
        if (e.getPassword() == null || e.getPassword().length() < 8) {
            return false;
        }
        return true;
    }


    public Users login(String email, String password) {
        Users user = dao.login(email, password);

        if (user != null) {
            System.out.println("login success");
            return user;
        }
        return null;
    }

    public Users getUser(String email) {
        Users user = dao.getUser(email);

        if (user != null) {
            System.out.println("login success");
            return user;
        }
        return null;
    }
    public List<Investment_Types>  getInvestments() {
        return investmentDao.getInvestmentTypes();
    }
    public Accounts getAccounts(int id) {
        return accountsDao.getAccount(id);
    }

    public Accounts getAccountFromCard(String cardNo) {
        return accountsDao.getAccountWithDebitCard(cardNo);
    }

    public Savings_Account getSavingsAccount(int id) {
        return accountsDao.getSavingsAccount(id);
    }
    public boolean createAccount(Accounts accounts) {
        try {
            accountsDao.createAccount(accounts);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean createSavingsAccount(Savings_Account accounts) {
        try {
            accountsDao.createSavings(accounts);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(Long id, Accounts accounts) {
        try {
            accountsDao.updateAccount(id, accounts);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateSavingsAccount(Long id, Savings_Account accounts) {
        try {
            accountsDao.updateSavingsAccount(id, accounts);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }
    public void updateUserId(int userId, String creditCard) {
        try {
            accountsDao.updateUserId(userId, creditCard);
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public boolean createProduct(Products products) {
        try {
            productDao.createProduct(products);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean makePurchase(Purchase purchase) {

        try {
            productDao.makePurchase(purchase);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean createInvestment(Investment_Details accounts) {
        try {
            investmentDao.createInvestment(accounts);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public Products getProducts(int id) {
        return productDao.getProducts(id);
    }

    public List<Purchase> getAllTransaction(int userId) {
       return productDao.getPurchase(userId);
    }


    public Purchase getTransaction(String id) {
        return productDao.getPurchase(id);
    }

    public List<Investment_Details> getInvestments(int userId) {
        return investmentDao.getInvestments(userId);
    }

    public boolean updatePurchaseStatus(int id, String status) {
        try {
            productDao.updatePurchaseStatus(id, status);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            return false;
        }
    }
}

