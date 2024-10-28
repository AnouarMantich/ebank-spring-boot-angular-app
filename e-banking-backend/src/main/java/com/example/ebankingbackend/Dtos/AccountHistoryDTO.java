package com.example.ebankingbackend.Dtos;

import lombok.Data;

import java.util.List;

/**
 * @author tensa
 **/
@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDTO> accountOperationDTOS;
}
