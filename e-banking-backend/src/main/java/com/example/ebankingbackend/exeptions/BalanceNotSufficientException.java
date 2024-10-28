package com.example.ebankingbackend.exeptions;

/**
 * @author tensa
 **/
public class BalanceNotSufficientException extends Throwable {
    public BalanceNotSufficientException(String balanceNotSufficient) {
        super(balanceNotSufficient);
    }
}
