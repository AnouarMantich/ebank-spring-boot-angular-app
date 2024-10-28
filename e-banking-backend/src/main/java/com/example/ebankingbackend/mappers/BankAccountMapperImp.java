package com.example.ebankingbackend.mappers;

import com.example.ebankingbackend.Dtos.AccountOperationDTO;
import com.example.ebankingbackend.Dtos.CurrentAccountDTO;
import com.example.ebankingbackend.Dtos.CustomerDTO;
import com.example.ebankingbackend.Dtos.SavingAccountDTO;
import com.example.ebankingbackend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author tensa
 **/
@Service
public class BankAccountMapperImp {
    public CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer toCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public SavingAccountDTO toSavingAccountDTO(SavingAccount savingAccount) {
        SavingAccountDTO savingAccountDTO=new SavingAccountDTO() ;
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(toCustomerDTO(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }

    public SavingAccount toSavingAccount(SavingAccountDTO savingAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(toCustomer(savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentAccountDTO toCurrentAccountDTO(CurrentAccount currentAccount) {
     CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
     BeanUtils.copyProperties(currentAccount, currentAccountDTO);
     currentAccountDTO.setCustomerDTO(toCustomerDTO(currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
     return currentAccountDTO;
    }

   public CurrentAccount toCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(toCustomer(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
   }

   public AccountOperationDTO toAccountOperationDTO(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        accountOperationDTO.setOperationTimestamp(accountOperation.getOperationTimestamp().toString());
        return accountOperationDTO;
   }
   public AccountOperation toAccountOperation(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        return accountOperation;
   }
}
