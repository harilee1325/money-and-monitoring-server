package com.example.social_bank.demo;


import com.example.social_bank.demo.account.AccountView;
import com.example.social_bank.demo.account.Accounts;
import com.example.social_bank.demo.account.Savings_Account;
import com.example.social_bank.demo.investment.InvestmentDao;
import com.example.social_bank.demo.investment.Investment_Details;
import com.example.social_bank.demo.investment.Investment_Types;
import com.example.social_bank.demo.investment.InvestmentView;
import com.example.social_bank.demo.products.*;
import com.example.social_bank.demo.user.LoginView;
import com.example.social_bank.demo.user.UserDao;
import com.example.social_bank.demo.user.UserView;
import com.example.social_bank.demo.user.Users;
import com.example.social_bank.demo.util.ErrorView;
import com.example.social_bank.demo.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

@org.springframework.stereotype.Controller
@RestController
@RequestMapping(path = "/api")
public class Controller {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    Services services;
    @Autowired
    UserDao dao;

    @Autowired
    ProductDao productDao;


    @Autowired
    InvestmentDao investmentDao;

    @GetMapping("/add")
    public String add(){
        return "success";

    }

    @CrossOrigin(origins = "http://localhost:3001")


    @PostMapping("/create")
    public ResponseEntity createEmployeePost(@RequestBody UserView userView) {
        logger.info("Creating user {}", userView.getName());
        Users emp = new Users();
        emp.setEmail(userView.getEmail());
        emp.setName(userView.getName());
        emp.setPassword(userView.getPassword());// hashing in dao
        emp.setMobileNumber(userView.getMobileNumber());
        emp.setUsername(userView.getUsername());
        Accounts accounts = services.getAccountFromCard(userView.getCreditCard());

        if (accounts!=null){
            if (services.createEmployee(emp)) {
                Users users = services.getUser(userView.getEmail());
                services.updateUserId(users.getId(), userView.getCreditCard() );
                return new ResponseEntity(new ErrorView("Account created"), HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity(new ErrorView("User does not exist"), HttpStatus.UNAUTHORIZED);

        }

        return new ResponseEntity(new ErrorView("Error"), HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin(origins = "http://localhost:3001")

    @GetMapping("/get_savings/{id}")
    public ResponseEntity getSavings(@PathVariable("id") int id){
        Savings_Account savingsAccount = services.getSavingsAccount(id);
        if (savingsAccount!=null){
            return new ResponseEntity(savingsAccount, HttpStatus.OK);

        }

        return new ResponseEntity(new ErrorView("Error"), HttpStatus.UNAUTHORIZED);


    }

    @CrossOrigin(origins = "http://localhost:3001")

    @PostMapping("/create_investment")
    public String createInvestment(@RequestBody InvestmentView investmentView) {
        logger.info("Creating investment {}", investmentView.getAmount());
        Investment_Details investmentDetails = new Investment_Details();
        investmentDetails.setAmount(investmentView.getAmount());
        investmentDetails.setType(investmentView.getType());
        investmentDetails.setUser_id(investmentView.getUserId());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String date = dtf.format(now);
        investmentDetails.setDate(date);
        Accounts acc = services.getAccounts(Integer.parseInt(investmentView.getUserId()));

        Accounts accounts = new Accounts();
        accounts.setUser_id(Integer.parseInt((investmentView.getUserId())));
        accounts.setCredit_card_number(acc.getCredit_card_number());
        accounts.setBalance(acc.getBalance());
        accounts.setWallet(acc.getWallet());


        Savings_Account savingsAccount = services.getSavingsAccount(Integer.parseInt(investmentView.getUserId()));
        Savings_Account savings_account = new Savings_Account();
        savings_account.setSavings_balance(savingsAccount.getSavings_balance() - Double.parseDouble(investmentView.getAmount()));
        savings_account.setUser_id(Integer.parseInt(investmentView.getUserId()));


        if (services.createInvestment(investmentDetails)) {
            if (services.updateAccount(acc.getId(), accounts)) {

                if (services.updateSavingsAccount(savingsAccount.getId(), savings_account)){
                    return "redirect:create?success=true";
                }
            }
            return "redirect:create?error=true";
        }
        return "redirect:create?error=true";
    }


    @PostMapping("/create_account")
    public String createAccount(@RequestBody AccountView accountView) {
        logger.info("Creating user {}", accountView.getUserId());


        Accounts acc = services.getAccounts(Integer.parseInt(accountView.getUserId()));
        Accounts accounts = new Accounts();
        accounts.setUser_id(Integer.parseInt((accountView.getUserId())));
        accounts.setCredit_card_number(((accountView.getCreditCard())));
        Savings_Account savingsAccount = services.getSavingsAccount(Integer.parseInt(accountView.getUserId()));
        Savings_Account savings_account = new Savings_Account();
        if (acc!=null && savingsAccount!=null){
            accounts.setBalance(acc.getBalance()+Double.parseDouble(accountView.getBalance()));
            accounts.setWallet(acc.getWallet()+Double.parseDouble(accountView.getWallet()));


            savings_account.setSavings_balance(savingsAccount.getSavings_balance() + Double.parseDouble(accountView.getSavings()));
            savings_account.setUser_id(Integer.parseInt(accountView.getUserId()));

            if (services.updateAccount(acc.getId(), accounts)) {

                if (services.updateSavingsAccount(savingsAccount.getId(), savings_account)){
                    return "redirect:create?success=true";
                }
            }
            return "redirect:create?error=true";
        }
        accounts.setBalance(Double.valueOf(accountView.getBalance()));
        accounts.setWallet(Double.valueOf(accountView.getWallet()));
        savings_account.setSavings_balance(Double.parseDouble(accountView.getSavings()));
        savings_account.setUser_id(Integer.parseInt(accountView.getUserId()));

        if (services.createAccount(accounts)) {
            if (services.createSavingsAccount(savings_account)){
                return "redirect:create?success=true";
            }
        }
        return "redirect:create?error=true";
    }




    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginView loginView) {
        Users user = services.login(loginView.getEmail(), loginView.getPassword());
        if (user!=null){
            return new ResponseEntity(new UserView(user.getEmail(), user.getName(), user.getId(), user.getUsername(), user.getMobile_number()), HttpStatus.OK);
        }
        return new ResponseEntity(new ErrorView("Cannot validate user"), HttpStatus.FORBIDDEN);

    }

    @PostMapping("/create_product")
    public String createProduct(@RequestBody ProductView productView) {
        logger.info("Creating product {}", productView.getProductName());
        Products products = new Products();
        products.setProduct_name(productView.getProductName());
        products.setProduct_price(productView.getProductPrice());
        if (services.createProduct(products)) {
            return "redirect:create?success=true";
        }
        return "redirect:create?error=true";
    }


    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/all")
    public ResponseEntity getAllProducts() {
        try{
            List<Products> products = productDao.getAllEmployees();
            return new ResponseEntity(products, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ErrorView("Error"), HttpStatus.FORBIDDEN);
        }
    }


    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/all_investments")
    public ResponseEntity getAllInvestmentTypes() {
        try{
            List<Investment_Types> products = services.getInvestments();
            return new ResponseEntity(products, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ErrorView("Error"), HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/investments/{user_id}")
    public ResponseEntity getInvestments(@PathVariable("user_id") int userId) {
        try{
            List<Investment_Details> products = services.getInvestments(userId);
            return new ResponseEntity(products, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ErrorView("Error"), HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/all_transaction/{user_id}")
    public ResponseEntity getAllTransactions(@PathVariable("user_id") int userId) {
        try{
            List<Purchase> products = services.getAllTransaction(userId);
            return new ResponseEntity(products, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ErrorView("Error"), HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/get_account/{user_id}")
    public ResponseEntity getSpecificAccount(@PathVariable("user_id") int id) {
        try{

            Accounts acc = services.getAccounts((id));

            return new ResponseEntity(acc, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ErrorView("Error"), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/admin/update_status/{p_id}")
    public String updateStatus(@PathVariable("p_id") int id){
        Purchase purchase = services.getTransaction(String.valueOf(id));

        Products products = services.getProducts(Integer.parseInt(purchase.getProduct_id()));
        double finalPrice = ceil(Float.parseFloat(products.getProduct_price()));

        double savingsBalance = (finalPrice - Float.parseFloat(products.getProduct_price()));

        logger.error("final price "+ finalPrice);
        logger.error("balance savings "+ Utils.round(savingsBalance, 2));


        Savings_Account savingsAccount = services.getSavingsAccount(Integer.parseInt(purchase.getUser_id()));
        Savings_Account savings_account = new Savings_Account();

        if (Objects.equals(purchase.getStatus(), "0")){

            logger.error("payment type 1");
            savings_account.setSavings_balance(savingsAccount.getSavings_balance() + Utils.round(savingsBalance, 2));
            savings_account.setUser_id(Integer.parseInt(purchase.getUser_id()));
                if (services.updateSavingsAccount(savingsAccount.getId(), savings_account)){
                    if (services.updatePurchaseStatus(id, "1")) {
                        return "redirect:create?success=true";

                    }
                return "redirect:create?error=true";
            }

        }
        return "redirect:create?error=true";

    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/purchase_product")
    public String makePurchase(@RequestBody PurchaseView purchaseView) {

        logger.info("Purchasing product {}", purchaseView.getProductId());

        Products products = services.getProducts(Integer.parseInt(purchaseView.getProductId()));

        double finalPrice = ceil(Float.parseFloat(products.getProduct_price()));

        double savingsBalance = (finalPrice - Float.parseFloat(products.getProduct_price()));

        logger.error("final price "+ finalPrice);
        logger.error("balance savings "+ Utils.round(savingsBalance, 2));

        Accounts acc = services.getAccounts(Integer.parseInt(purchaseView.getUserId()));

        Accounts accounts = new Accounts();
        accounts.setUser_id(Integer.parseInt((purchaseView.getUserId())));
        accounts.setCredit_card_number(acc.getCredit_card_number());

        Savings_Account savingsAccount = services.getSavingsAccount(Integer.parseInt(purchaseView.getUserId()));
        Savings_Account savings_account = new Savings_Account();

        if (Objects.equals(purchaseView.getPaymentType(), "1")){

            logger.error("payment type 1");

            accounts.setBalance(acc.getBalance()-finalPrice);
            accounts.setWallet(acc.getWallet());
            savings_account.setSavings_balance(savingsAccount.getSavings_balance());
            savings_account.setUser_id(Integer.parseInt(purchaseView.getUserId()));
            if (services.updateAccount(acc.getId(), accounts)) {
                Purchase purchase = new Purchase();
                purchase.setProduct_id(purchaseView.getProductId());
                purchase.setUser_id(purchaseView.getUserId());
                purchase.setPayment_type(purchaseView.getPaymentType());
                purchase.setProduct_price(String.valueOf(finalPrice));
                purchase.setSavings_amount(String.valueOf(savingsBalance));
                purchase.setStatus("0");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                String date = dtf.format(now);
                purchase.setDate(date);

                services.updateSavingsAccount(savingsAccount.getId(), savings_account);
                if (services.makePurchase(purchase)) {
                    return "redirect:create?success=true";
                }
                return "redirect:create?error=true";
            }

        }else{

            logger.error("payment type 2");
            accounts.setBalance(acc.getBalance());
            accounts.setWallet(acc.getWallet()-finalPrice);
            savings_account.setSavings_balance(savingsAccount.getSavings_balance());
            savings_account.setUser_id(Integer.parseInt(purchaseView.getUserId()));

            if (services.updateAccount(acc.getId(), accounts)) {
                Purchase purchase = new Purchase();
                purchase.setProduct_id(purchaseView.getProductId());
                purchase.setUser_id(purchaseView.getUserId());
                purchase.setPayment_type(purchaseView.getPaymentType());
                purchase.setProduct_price(String.valueOf(finalPrice));
                purchase.setStatus("1");
                purchase.setSavings_amount(String.valueOf(savingsBalance));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                String date = dtf.format(now);
                purchase.setDate(date);
                services.updateSavingsAccount(savingsAccount.getId(), savings_account);

                if (services.makePurchase(purchase)) {
                    return "redirect:create?success=true";
                }
                return "redirect:create?error=true";
            }
        }
        return "redirect:create?error=true";

    }



}
