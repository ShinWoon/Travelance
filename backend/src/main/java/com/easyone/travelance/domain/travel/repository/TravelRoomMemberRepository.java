package com.easyone.travelance.domain.travel.repository;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.entity.TravelRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TravelRoomMemberRepository extends JpaRepository<TravelRoomMember, Long> {

        List<TravelRoomMember> findAllByTravelRoom(TravelRoom travelRoom);

        TravelRoomMember findByTravelRoomAndMember(TravelRoom travelRoom, Member member);
        List<TravelRoomMember> findAllByMember(Member member);

        Optional<TravelRoomMember> findByTravelRoom_IdAndMember_Id(Long travelRoomId, Long memberId);
        List<TravelRoomMember> findByTravelRoom(TravelRoom travelRoom);
        @Query("SELECT trm FROM TravelRoomMember trm JOIN trm.member m WHERE trm.travelRoom.id = :travelRoomId AND m.email = :email")
        Optional<TravelRoomMember> findByTravelRoom_IdAndMember_Email(@Param("travelRoomId") Long travelRoomId, @Param("email") String email);

}
