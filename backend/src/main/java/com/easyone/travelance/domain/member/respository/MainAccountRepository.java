package com.easyone.travelance.domain.member.respository;

import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainAccountRepository extends JpaRepository<MainAccount, Long> {
    Optional<MainAccount> findByMemberId(Long memberId);

    // 회원에 해당하는 MainAccount를 삭제하는 메서드
    void deleteByMember(Member member);

}
