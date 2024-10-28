package com.example.ebankingbackend.services;

import com.example.ebankingbackend.Dtos.*;
import com.example.ebankingbackend.entities.BankAccount;
import com.example.ebankingbackend.entities.CurrentAccount;
import com.example.ebankingbackend.entities.SavingAccount;
import com.example.ebankingbackend.exeptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exeptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exeptions.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author tensa
 **/
public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> searchCustomers(String name);

    CurrentAccountDTO saveCurrentAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingAccount(double initialBalance, Long customerId, double interestRate ) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount,String description) throws BankAccountNotFoundException;
    void transfer(String fromAccountId, String toAccountId, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId) throws CustomerNotFoundException;
    List<BankAccountDTO> bankAccountList();

    List<AccountOperationDTO> accountHistory(String accountId);


    AccountHistoryDTO AccountHistoryDTO(String accountId, int page, int size) throws BankAccountNotFoundException;

    CustomerAccountsDTO CustomerAccounts(Long customerId) throws CustomerNotFoundException;
}
