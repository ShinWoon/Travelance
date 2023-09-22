package com.easyone.travelance.domain.travel.controller;

import com.easyone.travelance.domain.travel.dto.NoticeAllResponseDto;
import com.easyone.travelance.domain.travel.dto.NoticeRequestDto;
import com.easyone.travelance.domain.travel.dto.NoticeUpdateRequestDto;
import com.easyone.travelance.domain.travel.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/{roomId}")
    @Operation(summary = "공지사항 전체 조회")
    public ResponseEntity<List> getAllNotice(@PathVariable Long roomId){
        List<NoticeAllResponseDto> noticeList = noticeService.getAllNotice(roomId);
        return new ResponseEntity<>(noticeList, HttpStatus.OK);
    }

    @GetMapping("/{roomId}/{noticeId}")
    @Operation(summary = "공지사항 단일 조회")
    public ResponseEntity<NoticeAllResponseDto> getOneNotice(@PathVariable Long roomId, Long noticeId){
        NoticeAllResponseDto notice = noticeService.getOneNotice(roomId, noticeId);
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "공지사항 등록")
    public ResponseEntity<String> saveNotice(@RequestPart NoticeRequestDto noticeRequestDto){
        String response = noticeService.saveNotice(noticeRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{roomId}")
    @Operation(summary = "공지사항 수정")
    public ResponseEntity<String> patchNotice(@PathVariable Long roomId,
    @RequestBody NoticeUpdateRequestDto noticeUpdateRequestDto){
        String response = noticeService.patchNotice(roomId, noticeUpdateRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "공지사항 삭제")
    public ResponseEntity<String> deleteNotice(@PathVariable Long roomId){
        String response = noticeService.deleteNotice(roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
