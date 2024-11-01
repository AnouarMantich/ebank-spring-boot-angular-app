package com.example.ebankingbackend.repositories;

import com.example.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tensa
 **/
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContains(String firstName);
}
