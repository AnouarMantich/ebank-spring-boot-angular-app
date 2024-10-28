package com.example.ebankingbackend.Dtos;

import com.example.ebankingbackend.enums.AccountStatus;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author tensa
 **/
@Data
public  class CurrentAccountDTO extends BankAccountDTO{

    private double interestRate;

}
