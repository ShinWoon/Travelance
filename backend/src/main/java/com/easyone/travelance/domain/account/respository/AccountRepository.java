package com.easyone.travelance.domain.account.respository;

import com.easyone.travelance.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
