package com.example.ebankingbackend.Dtos;

import lombok.Data;

/**
 * @author tensa
 **/
@Data
public class TransferRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
}
