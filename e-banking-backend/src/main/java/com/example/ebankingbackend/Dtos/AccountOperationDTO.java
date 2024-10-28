package com.example.ebankingbackend.Dtos;
import com.example.ebankingbackend.enums.OperationsType;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author tensa
 **/

@Data
public class AccountOperationDTO {
    private Long id ;
    private String operationTimestamp ;
    private double amount ;
    private OperationsType type;
    private String description ;
}
