package com.example.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tensa
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("CURR")
public class CurrentAccount extends BankAccount {
    private double overDraft;
}
