package com.example.ebankingbackend.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author tensa
 **/
@Data @AllArgsConstructor
public class CustomerAccountsDTO {
    private CustomerDTO customer;
    private List<BankAccountDTO> bankAccounts;
}
