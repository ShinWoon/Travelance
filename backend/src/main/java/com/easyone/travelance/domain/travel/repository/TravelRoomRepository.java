package com.easyone.travelance.domain.travel.repository;


import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelRoomRepository extends JpaRepository<TravelRoom, Long> {

    @Query("SELECT tr FROM TravelRoom tr JOIN tr.travelRoomMembers trm WHERE tr.id = :roomId AND trm.member.id = :memberId")
    Optional<TravelRoom> findByIdAndMemberId(@Param("roomId") Long roomId, @Param("memberId") Long memberId);

    @Query("SELECT tr FROM TravelRoom tr JOIN tr.travelRoomMembers trm JOIN trm.member m WHERE m.email = :email AND tr.id = :id")
    Optional<TravelRoom> findByIdAndMemberEmail(@Param("id") Long id, @Param("email") String email);

    List<TravelRoom> findAllByTravelRoomMembers_MemberAndIsDone(Member member, RoomType roomType);

    List<TravelRoom> findAllByTravelRoomMembersMember(Member member);
}