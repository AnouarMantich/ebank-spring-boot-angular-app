package com.example.ebankingbackend.exeptions;

/**
 * @author tensa
 **/
public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String bankAccountNotFound) {
        super(bankAccountNotFound);
    }
}
