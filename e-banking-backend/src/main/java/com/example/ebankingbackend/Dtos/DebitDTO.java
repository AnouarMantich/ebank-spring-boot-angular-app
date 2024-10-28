package com.example.ebankingbackend.Dtos;

import lombok.Data;

/**
 * @author tensa
 **/
@Data
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
}
