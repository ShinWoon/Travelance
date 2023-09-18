package com.easyone.travelance.domain.member.service;


import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.domain.member.dto.MemberDto;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberService memberService;
    
    // 추가정보 업데이트
    @Transactional
    public MemberDto.Response updateAdditionalInfo(MemberDto.UpdateRequest request, String email) {
        Member member = memberService.findMemberByEmail(email);

        member.updateRole(Role.MEMBER);

        return MemberDto.Response.builder()
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
