package com.easyone.travelance.domain.member.respository;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberAuthRepository extends JpaRepository<MemberAuth, Long> {

    Optional<MemberAuth> findByRefreshToken(String refreshToken);
}