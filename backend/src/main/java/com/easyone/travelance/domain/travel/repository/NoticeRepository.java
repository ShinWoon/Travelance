package com.easyone.travelance.domain.travel.repository;

import com.easyone.travelance.domain.travel.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByTravelRoomId(Long roomId);
    Optional<Notice> findByIdAndTravelRoomId(Long noticeId, Long roomId);
}
