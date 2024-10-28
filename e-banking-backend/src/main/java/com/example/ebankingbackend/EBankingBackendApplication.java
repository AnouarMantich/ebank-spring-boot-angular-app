package com.example.ebankingbackend;

import com.example.ebankingbackend.Dtos.CustomerDTO;
import com.example.ebankingbackend.entities.BankAccount;
import com.example.ebankingbackend.entities.CurrentAccount;
import com.example.ebankingbackend.entities.SavingAccount;
import com.example.ebankingbackend.enums.AccountStatus;
import com.example.ebankingbackend.exeptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exeptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exeptions.CustomerNotFoundException;
import com.example.ebankingbackend.mappers.BankAccountMapperImp;
import com.example.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }




    @Bean
    CommandLineRunner start(BankAccountService bankAccountService, BankAccountMapperImp dtoMapper) {
        return args -> {
            Stream.of("mohamed","amine","imane","yassine","oumaima","fouad").forEach(name->{
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customerDTO);
            });

            bankAccountService.searchCustomers("").forEach(customerDTO->{
                try {
                  CurrentAccount bankAccount1=dtoMapper.toCurrentAccount( bankAccountService.saveCurrentAccount(Math.random()*10000,customerDTO.getId(),5000));
                  SavingAccount bankAccount2=dtoMapper.toSavingAccount(  bankAccountService.saveSavingAccount(Math.random()*10000,customerDTO.getId(),2.5));

                    for (int i = 0; i < 5; i++) {
                        bankAccountService.credit(bankAccount1.getId(),Math.random()*10000+10000,"description");
                        bankAccountService.debit(bankAccount1.getId(),Math.random()*10000,"description");
                        bankAccountService.credit(bankAccount2.getId(),Math.random()*10000+10000,"description");
                        bankAccountService.debit(bankAccount2.getId(),Math.random()*10000,"description");
                    }

                } catch (CustomerNotFoundException | BankAccountNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (BalanceNotSufficientException e) {
                    throw new RuntimeException(e);
                }

            });
        };
    }

}
