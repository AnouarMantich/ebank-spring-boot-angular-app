package com.example.ebankingbackend.web;

import com.example.ebankingbackend.Dtos.CustomerDTO;
import com.example.ebankingbackend.exeptions.CustomerNotFoundException;
import com.example.ebankingbackend.services.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tensa
 **/
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {
    public BankAccountServiceImpl bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> getCustomers() {
        return this.bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword) {
        return this.bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDTO getCustomer(@PathVariable("id") Long id) throws CustomerNotFoundException {
        return this.bankAccountService.getCustomer(id);
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }


    @PutMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(id);
        return this.bankAccountService.updateCustomer(customerDTO);
    }


    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(id);
    }

}
