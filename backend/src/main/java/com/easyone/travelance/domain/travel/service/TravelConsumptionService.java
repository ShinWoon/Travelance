package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.travel.dto.ConsumptionResponseDto;
import com.easyone.travelance.domain.travel.entity.Consumption;
import com.easyone.travelance.domain.travel.repository.ConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TravelConsumptionService {

    private final ConsumptionRepository consumptionRepository;

    @Transactional(readOnly = true)
    public List<ConsumptionResponseDto> findByTravelId(Member member, Long roomId) {
        return consumptionRepository.findByRoomId(roomId).stream()
                .map(consumption -> new ConsumptionResponseDto(consumption, member))
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public Long TotalPriceTravelId(Long roomId) {
        List<Consumption> consumptions = consumptionRepository.findByRoomId(roomId);
        Long totalprice = consumptions.stream()
                .mapToLong(Consumption::getPrice)
                .sum();
        return totalprice;
    }

}
