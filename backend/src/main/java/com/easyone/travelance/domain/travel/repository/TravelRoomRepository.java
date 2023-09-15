package com.easyone.travelance.domain.travel.repository;


import com.easyone.travelance.domain.travel.dto.RoomAllResponseDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRoomRepository extends JpaRepository<TravelRoom, Long> {

    List<TravelRoom> findAllOrderByIdDesc();
}
