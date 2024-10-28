package com.example.ebankingbackend.services;

import com.example.ebankingbackend.Dtos.*;
import com.example.ebankingbackend.entities.*;
import com.example.ebankingbackend.enums.OperationsType;
import com.example.ebankingbackend.exeptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exeptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exeptions.CustomerNotFoundException;
import com.example.ebankingbackend.mappers.BankAccountMapperImp;
import com.example.ebankingbackend.repositories.AccountOperationRepository;
import com.example.ebankingbackend.repositories.BankAccountRepository;
import com.example.ebankingbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author tensa
 **/
@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImp dtoMapper;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.toCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.toCustomerDTO(savedCustomer);
    }


    @Override
    public List<CustomerDTO> searchCustomers(String keyword){
        List<Customer> customers = customerRepository.findByNameContains(keyword);
       return   customers.stream().map(customer -> dtoMapper.toCustomerDTO(customer)).collect(Collectors.toList());

    }

    @Override
    public CurrentAccountDTO saveCurrentAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(LocalDate.now());
        CurrentAccount savedCurrentAccount=bankAccountRepository.save(currentAccount);

        return dtoMapper.toCurrentAccountDTO(savedCurrentAccount);
    }

    @Override
    public SavingAccountDTO saveSavingAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(LocalDate.now());
        SavingAccount savedSavingAccount=bankAccountRepository.save(savingAccount);

        return dtoMapper.toSavingAccountDTO(savedSavingAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
      return   customerRepository.findAll().stream().map(customer -> dtoMapper.toCustomerDTO(customer)).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank account not found"));

        if(bankAccount instanceof SavingAccount savingAccount){
            return dtoMapper.toSavingAccountDTO(savingAccount);
        }
        if(bankAccount instanceof CurrentAccount currentAccount){
            return dtoMapper.toCurrentAccountDTO(currentAccount);
        }
        return null;

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException,BalanceNotSufficientException {
        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank account not found"));
        if(bankAccount.getBalance()<amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationTimestamp(LocalDateTime.now());
        accountOperation.setType(OperationsType.DEBIT);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {

        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank account not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperationTimestamp(LocalDateTime.now());
        accountOperation.setType(OperationsType.CREDIT);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String fromAccountId, String toAccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
         debit(fromAccountId,amount,description);
         credit(toAccountId,amount,description);
    }

    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccountList=bankAccountRepository.findAll();
     return   bankAccountList.stream().map(account->{
           if(account instanceof SavingAccount savingAccount){
            return   dtoMapper.toSavingAccountDTO(savingAccount);
           } if(account instanceof CurrentAccount currentAccount){
               return   dtoMapper.toCurrentAccountDTO(currentAccount);
            }
           return null;
        }).collect(Collectors.toList());

    }

    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(id).orElseThrow(()->{
            return new CustomerNotFoundException("Customer not found");
        });
        return dtoMapper.toCustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.toCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.toCustomerDTO(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("customer not found !"));
        customerRepository.delete(customer);

    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
       return accountOperationRepository.findByBankAccountId(accountId)
                .stream().map(operation->{
                    return dtoMapper.toAccountOperationDTO(operation);
                }).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO AccountHistoryDTO(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->{
            return new BankAccountNotFoundException("Bank account not found");
        });
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationTimestampDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS=accountOperations.getContent().stream().map(operation->dtoMapper.toAccountOperationDTO(operation)).toList();
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }


    @Override
    public CustomerAccountsDTO CustomerAccounts(Long customerId) throws CustomerNotFoundException {
       Customer customer= this.customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer Not found"));
        CustomerDTO customerDTO=dtoMapper.toCustomerDTO(customer);
        List<BankAccountDTO> bankAccountDTOS=new ArrayList<>();
        List<BankAccount> bankAccounts =this.bankAccountRepository.findByCustomerId(customerId);
        bankAccounts.forEach(account->{
            if(account instanceof SavingAccount savingAccount){
                bankAccountDTOS.add(dtoMapper.toSavingAccountDTO(savingAccount));
            }
            if(account instanceof CurrentAccount currentAccount){
                bankAccountDTOS.add(dtoMapper.toCurrentAccountDTO(currentAccount));
            }
        });
        return new CustomerAccountsDTO(customerDTO,bankAccountDTOS);

    }
 

}
