package com.easyone.travelance.domain.travel.repository;


import com.easyone.travelance.domain.travel.entity.TravelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRoomRepository extends JpaRepository<TravelRoom, Long> {

    Optional<TravelRoom> findByIdAndMemberId(Long roomNumber, Long memberId);
}
