package com.easyone.travelance.domain.travel.repository;


import com.easyone.travelance.domain.travel.entity.TravelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRoomRepository extends JpaRepository<TravelRoom, Long> {

    @Query("SELECT tr FROM TravelRoom tr JOIN tr.travelRoomMembers trm WHERE tr.id = :roomId AND trm.member.id = :memberId")
    Optional<TravelRoom> findByIdAndMemberId(@Param("roomId") Long roomId, @Param("memberId") Long memberId);
}
