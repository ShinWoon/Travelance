package com.easyone.travelance.domain.account.respository;

import com.easyone.travelance.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByAccount(String account);

}
