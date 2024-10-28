package com.example.ebankingbackend.entities;

import com.example.ebankingbackend.enums.OperationsType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author tensa
 **/
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime operationTimestamp ;
    private double amount ;
    @Enumerated(EnumType.STRING)
    private OperationsType type;
    @ManyToOne
    private BankAccount bankAccount ;
    private String description ;
}
