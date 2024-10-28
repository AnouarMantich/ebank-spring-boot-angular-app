package com.example.ebankingbackend.Dtos;

import com.example.ebankingbackend.enums.AccountStatus;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author tensa
 **/
@Data
public class BankAccountDTO {
    private String id;
    private double balance;
    private LocalDate createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private String type;
}
