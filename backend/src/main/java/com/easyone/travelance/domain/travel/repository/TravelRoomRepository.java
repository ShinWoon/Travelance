package com.easyone.travelance.domain.travel.repository;

import com.easyone.travelance.domain.travel.entity.TravelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelRoomRepository extends JpaRepository<TravelRoom, Long> {

    Optional<TravelRoom> findByRoomNumber(Long roomNumber);
}
