package com.example.ebankingbackend.exeptions;

/**
 * @author tensa
 **/
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
