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
        //추후 변경
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
                  Long totalPrice = travelPaymentService.TotalPriceTravelId(entity.getRoomNumber());
                  return new RoomAllResponseDto(entity, totalPrice);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoomStaticResponseDto findById(Long roomId) {
        //추후 변경
        Long memberid =1L;
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberid));

        TravelRoom travelRoom = travelRoomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행방이 없습니다. id =" + roomId));

        Long budget = travelRoom.getBudget();
        Long UseTotal = travelPaymentService.TotalPriceTravelId(roomId);

        Long budgetPer = UseTotal / budget;
        Long rest = budget-UseTotal;

        //전체 소비내역
        List<PaymentResponseDto> everyUse = travelPaymentService.findByTravelId(member, roomId);

        //내 소비내역


        return new RoomStaticResponseDto(travelRoom, member, budgetPer, UseTotal, rest);
    }



    //    @Transactional
    //    public ArticleDetailResponseDto findById(ArticleType type, Long memberId, Long articleId){
    //        Member member = memberRepository.findById(memberId)
    //                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id =" + memberId));
    //
    //        Article article = articleRepository.findByIdAndType(articleId, type)
    //                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + articleId));
    //        articleRepository.updateView(articleId);
    //        List<ArticleCommentResponseDto> comments = articleCommentService.findByArticleId(member, articleId);
    //        return new ArticleDetailResponseDto(article, member, comments);
    //    }

}
