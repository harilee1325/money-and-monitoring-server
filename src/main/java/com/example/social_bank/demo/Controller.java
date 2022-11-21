package com.example.social_bank.demo;


import com.example.social_bank.demo.account.AccountView;
import com.example.social_bank.demo.account.Accounts;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.ceil;

@org.springframework.stereotype.Controller
@RestController
@RequestMapping(path = "/social")
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
    public String createEmployeePost(@RequestBody UserView userView) {
        logger.info("Creating user {}", userView.getName());
        Users emp = new Users();
        emp.setEmail(userView.getEmail());
        emp.setName(userView.getName());
        emp.setPassword(userView.getPassword());// hashing in dao
        emp.setMobileNumber(userView.getMobileNumber());
        emp.setUsername(userView.getUsername());
        if (services.createEmployee(emp)) {
            return "redirect:create?success=true";
        }
        return "redirect:create?error=true";
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
        accounts.setSavings_balance(acc.getSavings_balance() - Double.parseDouble(investmentView.getAmount()));
        accounts.setDebit_card_number(acc.getDebit_card_number());
        accounts.setBalance(acc.getBalance());
        accounts.setWallet(acc.getWallet());



        if (services.createInvestment(investmentDetails)) {
            if (services.updateAccount(acc.getId(), accounts)) {
                return "redirect:create?success=true";
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
        accounts.setDebit_card_number(Integer.parseInt((accountView.getDebitCard())));

        if (acc!=null){
            accounts.setBalance(acc.getBalance()+Double.parseDouble(accountView.getBalance()));
            accounts.setWallet(acc.getWallet()+Double.parseDouble(accountView.getWallet()));
            accounts.setSavings_balance(acc.getSavings_balance()+Double.parseDouble(accountView.getSavings()));
            if (services.updateAccount(acc.getId(), accounts)) {
                return "redirect:create?success=true";
            }
            return "redirect:create?error=true";
        }
        accounts.setBalance(Double.valueOf(accountView.getBalance()));
        accounts.setWallet(Double.valueOf(accountView.getWallet()));
        accounts.setSavings_balance(Double.valueOf(accountView.getSavings()));
        if (services.createAccount(accounts)) {
            return "redirect:create?success=true";
        }
        return "redirect:create?error=true";
    }




    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginView loginView) {
        Users user = services.login(loginView.getEmail(), loginView.getPassword());
        if (user!=null){
            return new ResponseEntity(new UserView(user.getEmail(), user.getName(), user.getId(), user.getUsername()), HttpStatus.OK);
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

    @GetMapping("/all_transaction/{user_id}")
    public ResponseEntity getAllTransactions(@PathVariable("user_id") int userId) {
        try{
            List<Purchase> products = services.getAllTransaction(userId);
            return new ResponseEntity(products, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ErrorView("Error"), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/get_account/{user_id}")
    public ResponseEntity getSpecificAccount(@PathVariable("user_id") int id) {
        try{

            Accounts acc = services.getAccounts((id));

            return new ResponseEntity(new AccountView(String.valueOf(acc.getBalance()), String.valueOf(acc.getWallet()), String.valueOf(acc.getSavings_balance()), String.valueOf(acc.getUser_id()), String.valueOf(acc.getDebit_card_number())), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ErrorView("Error"), HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/purchase_product")
    public String makePurchase(@RequestBody PurchaseView purchaseView) {

        logger.info("Purchasing product {}", purchaseView.getProductId());

        Products products = services.getProducts(Integer.parseInt(purchaseView.getProductId()));

        Double finalPrice = ceil(Double.parseDouble(products.getProduct_price()));

        Double savingsBalance = (finalPrice - Double.parseDouble(products.getProduct_price()));

        logger.error("final price "+ finalPrice);
        logger.error("balance savings "+ savingsBalance);

        Accounts acc = services.getAccounts(Integer.parseInt(purchaseView.getUserId()));

        Accounts accounts = new Accounts();
        accounts.setUser_id(Integer.parseInt((purchaseView.getUserId())));
        accounts.setDebit_card_number(acc.getDebit_card_number());

        if (Objects.equals(purchaseView.getPaymentType(), "1")){

            logger.error("payment type 1");

            accounts.setBalance(acc.getBalance()-finalPrice);
            accounts.setWallet(acc.getWallet());
            accounts.setSavings_balance(acc.getSavings_balance()+savingsBalance);

            if (services.updateAccount(acc.getId(), accounts)) {
                Purchase purchase = new Purchase();
                purchase.setProduct_id(purchaseView.getProductId());
                purchase.setUser_id(purchaseView.getUserId());
                purchase.setPayment_type(purchaseView.getPaymentType());
                purchase.setProduct_price(String.valueOf(finalPrice));
                purchase.setSavings_amount(String.valueOf(savingsBalance));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                String date = dtf.format(now);
                purchase.setDate(date);

                if (services.makePurchase(purchase)) {
                    return "redirect:create?success=true";
                }
                return "redirect:create?error=true";
            }

        }else{

            logger.error("payment type 2");
            accounts.setBalance(acc.getBalance());
            accounts.setWallet(acc.getWallet()-finalPrice);
            accounts.setSavings_balance(acc.getSavings_balance());

            if (services.updateAccount(acc.getId(), accounts)) {
                Purchase purchase = new Purchase();
                purchase.setProduct_id(purchaseView.getProductId());
                purchase.setUser_id(purchaseView.getUserId());
                purchase.setPayment_type(purchaseView.getPaymentType());
                purchase.setProduct_price(String.valueOf(finalPrice));
                purchase.setSavings_amount(String.valueOf(savingsBalance));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                String date = dtf.format(now);
                purchase.setDate(date);
                if (services.makePurchase(purchase)) {
                    return "redirect:create?success=true";
                }
                return "redirect:create?error=true";
            }
        }
        return "redirect:create?error=true";

    }



}
