package com.example.ebankingbackend.repositories;

import com.example.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tensa
 **/
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomerId(Long customerId);
}
