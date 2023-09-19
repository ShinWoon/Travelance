package com.easyone.travelance.domain.member.respository;

import com.easyone.travelance.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    Optional<List<Member>> findByNicknameContaining(String nickname);
    Optional<Member> findByRefreshToken(String refreshToken);
    Optional<Member> findById(Long memberId);

    Optional<Member> findByPrivateId(String privateId);


}
