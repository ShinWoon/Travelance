package com.easyone.travelance.domain.travel.controller;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.travel.dto.MapAllResponseDto;
import com.easyone.travelance.domain.travel.dto.PaymentResponseDto;
import com.easyone.travelance.domain.travel.dto.RoomStaticResponseDto;
import com.easyone.travelance.domain.travel.service.MapService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/travel/map")
public class MapController {
    private final MemberService memberService;
    private final MapService mapService;

    // 지금 여행중인 여행방에 대한 결제정보를 전달한다.
    @Operation(summary = "여행방의 모든 결제장소 조회", description = "요청 시, 현재 여행중인 여행방에 대한 결제내역 주소를 전달합니다. ")
    @GetMapping(value = "/{roomId}")
    public ResponseEntity<List<MapAllResponseDto>> getPaymentForMap(@PathVariable Long roomId) {
        List<MapAllResponseDto> responseDto = mapService.mapList(roomId);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

//    @Operation(summary = "해당 장소의 결제 정보 조회", description = "해당 장소요청 시, 현재 여행중인 여행방에 대한 결제내역 주소를 전달합니다. ")
//    @GetMapping(value = "")
//    public ResponseEntity<List<MapAllResponseDto>> getPaymentForMap(@MemberInfo MemberInfoDto memberInfo) {
//        Member member = memberService.findMemberByEmail(memberInfo.getEmail());
//        List<MapAllResponseDto> responseDto = mapService.mapList(member);
//        return new ResponseEntity<>(responseDto,HttpStatus.OK);
//    }


}
