package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.travel.dto.NoticeAllResponseDto;
import com.easyone.travelance.domain.travel.dto.NoticeRequestDto;
import com.easyone.travelance.domain.travel.dto.NoticeUpdateRequestDto;
import com.easyone.travelance.domain.travel.entity.Notice;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.repository.NoticeRepository;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private TravelRoomRepository travelRoomRepository;

//    @Cacheable(value = "allNotices", key="#roomId")
    public List<NoticeAllResponseDto> getAllNotice(Long roomId){
        List<Notice> notices = noticeRepository.findByTravelRoomId(roomId);
        return notices.stream()
                .map(NoticeAllResponseDto::new)
                .collect(Collectors.toList());
    }
    public NoticeAllResponseDto getOneNotice(Long roomId, Long noticeId){
        Notice notice = noticeRepository.findByIdAndTravelRoomId(noticeId, roomId)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));
        return new NoticeAllResponseDto(notice);
    }

//    @CacheEvict(value = "allNotices", key="#noticeRequestDto.roomId")
    public String saveNotice(NoticeRequestDto noticeRequestDto){
        Optional<TravelRoom> travelRoomOpt = travelRoomRepository.findById(noticeRequestDto.getRoomId());
        if (travelRoomOpt.isEmpty()){
            throw new EntityNotFoundException("여행방이 존재하지 않습니다.");
        }
        TravelRoom travelRoom = travelRoomOpt.get();

        Notice notice = new Notice();
        notice.setTravelRoom(travelRoom);
        notice.setTitle(noticeRequestDto.getTitle());
        notice.setContent(noticeRequestDto.getContent());
        notice.setLink(noticeRequestDto.getLink());

        noticeRepository.save(notice);

        return "공지사항 등록 성공";
    }
//    @CacheEvict(value = "allNotices", key="#roomId")
    public String patchNotice(Long roomId, NoticeUpdateRequestDto noticeUpdateRequestDto) {
        Optional<TravelRoom> optionalTravelRoom = travelRoomRepository.findById(roomId);
        if (optionalTravelRoom.isEmpty()){
            throw new EntityNotFoundException("여행방이 존재하지 않습니다.");
        }

        Optional<Notice> optionalNotice = noticeRepository.findById(noticeUpdateRequestDto.getNoticeId());

        if (optionalNotice.isEmpty()) {
            throw new EntityNotFoundException("공지사항이 존재하지 않습니다.");
        }

        Notice notice = optionalNotice.get();
        notice.setTitle(noticeUpdateRequestDto.getTitle());
        notice.setContent(noticeUpdateRequestDto.getContent());
        notice.setLink(noticeUpdateRequestDto.getLink());

        noticeRepository.save(notice);

        return "공지사항이 수정되었습니다.";
    }

//    @CacheEvict(value = "allNotices", key="#roomId")
    public String deleteNotice(Long roomId, Long noticeId){
        Optional<TravelRoom> optionalTravelRoom = travelRoomRepository.findById(roomId);
        if (optionalTravelRoom.isEmpty()){
            throw new EntityNotFoundException("여행방이 존재하지 않습니다.");
        }
        Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);

        if (optionalNotice.isEmpty()){
            throw new EntityNotFoundException("공지사항이 존재하지 않습니다.");
        }

        noticeRepository.deleteById(optionalNotice.get().getId());

        return "공지사항이 삭제되었습니다.";
    }
}
