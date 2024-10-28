package com.example.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tensa
 **/
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@DiscriminatorValue("SAV")
public class SavingAccount extends BankAccount {
    private double interestRate;
}
