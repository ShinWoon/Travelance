package com.easyone.travelance.domain.travel.repository;

import com.easyone.travelance.domain.travel.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
    List<Consumption> findByRoomId(Long roomId);

}
