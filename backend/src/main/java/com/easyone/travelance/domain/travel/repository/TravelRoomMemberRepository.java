package com.easyone.travelance.domain.travel.repository;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRoomMemberRepository extends JpaRepository<TravelRoomMember, Long> {
        boolean existsByMember(Member member);
}
