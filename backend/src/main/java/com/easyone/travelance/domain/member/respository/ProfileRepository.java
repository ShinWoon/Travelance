package com.easyone.travelance.domain.member.respository;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByMember(Member member);

    Profile findByMemberAndTravelRoom(Member member, TravelRoom travelRoom);

    // 회원에 해당하는 MainAccount를 삭제하는 메서드
    void deleteAllByMember(Member member);
}
