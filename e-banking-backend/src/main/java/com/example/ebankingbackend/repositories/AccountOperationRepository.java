package com.example.ebankingbackend.repositories;

import com.example.ebankingbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tensa
 **/
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long  > {
    List<AccountOperation> findByBankAccountId(String accountId);
    Page<AccountOperation> findByBankAccountIdOrderByOperationTimestampDesc(String accountId, Pageable pageable);
}
