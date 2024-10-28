package com.example.ebankingbackend.web;

import com.example.ebankingbackend.Dtos.*;
import com.example.ebankingbackend.exeptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exeptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exeptions.CustomerNotFoundException;
import com.example.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tensa
 **/
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listBankAccounts() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountOperations(@PathVariable String accountId,
                                                  @RequestParam(name="page",defaultValue = "0") int page,
                                                  @RequestParam(name = "size",defaultValue = "5") int size
    ) throws BankAccountNotFoundException {
        return bankAccountService.AccountHistoryDTO(accountId,page,size);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
     return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount(),
                transferRequestDTO.getDescription()
        );
    }

    @GetMapping("/customerAccounts/{id}")
    public CustomerAccountsDTO getCustomerAccounts(@PathVariable Long id) throws CustomerNotFoundException {
        return this.bankAccountService.CustomerAccounts(id);
    }




}
