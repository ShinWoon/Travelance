package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.travel.dto.PaymentResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomAllResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomStaticResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomInfoRequestDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.domain.travel.enumclass.RoomType;
import com.easyone.travelance.domain.travel.repository.TravelRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TravelService {
    private final TravelRoomRepository travelRoomRepository;
    private final MemberRepository memberRepository;
    private final TravelPaymentService travelPaymentService;

    //방만들기
    @Transactional
    public void save(RoomInfoRequestDto roomInfoRequestDto) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        //방 만든 직전에는 사전정산 상태
        RoomType roomType = RoomType.BEFORE;
        TravelRoom room = travelRoomRepository.save(roomInfoRequestDto.toEntity(roomType, member));
    }

    @Transactional(readOnly = true)
    public List<RoomAllResponseDto> findAllDesc() {
        return travelRoomRepository.findAllOrderByIdDesc().stream()
                .map(entity -> {
                  Long totalPrice = travelPaymentService.TotalPriceTravelId(entity.getId());
                  return new RoomAllResponseDto(entity, totalPrice);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoomStaticResponseDto findById(Long roomId) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        Long budget = travelRoom.getBudget();
        Long UseTotal = travelPaymentService.TotalPriceTravelId(roomId);

        //예산 대비 사용 비율
        Long budgetPer = UseTotal / budget;
        // 남는 금액
        Long rest = budget-UseTotal;

        //전체 소비내역
        List<PaymentResponseDto> everyUse = travelPaymentService.findByTravelId(member, roomId);

        //내 소비내역
        List<PaymentResponseDto> myUse = travelPaymentService.findByTravelIdAndMemberId(member, roomId);

        return new RoomStaticResponseDto(travelRoom, member, budgetPer, UseTotal, rest, everyUse, myUse);
    }

    @Transactional
    public void updateRoom(RoomInfoRequestDto roomInfoRequestDto, Long roomId) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        /** 추후변경  참여자인 사람은 모두 수정할 수 있도록*/
        if(member.equals(travelRoom.getMember())) {
            travelRoom.update(roomInfoRequestDto);
        }
        else {
//            throw new UserNotAuthorizedException("해당 멤버는 게시글 작성자가 아닙니다.");
        }

    }

    @Transactional
    public void delete(Long roomId) {
        /** 추후변경 */
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        /** 추후변경  참여자인 사람은 모두 수정할 수 있도록*/
        if(member.equals(travelRoom.getMember())) {
            travelRoomRepository.delete(travelRoom);
        }
        else {
//            throw new UserNotAuthorizedException("해당 멤버는 게시글 작성자가 아닙니다.");
        }
    }
}
