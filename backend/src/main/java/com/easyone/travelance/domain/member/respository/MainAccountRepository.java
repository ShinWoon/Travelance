package com.easyone.travelance.domain.member.respository;

import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MainAccountRepository extends JpaRepository<MainAccount, Long> {

}
