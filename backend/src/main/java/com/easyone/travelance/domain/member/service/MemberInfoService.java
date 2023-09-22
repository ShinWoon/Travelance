package com.easyone.travelance.domain.member.service;


import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.domain.member.dto.MemberDto;
import com.easyone.travelance.domain.member.dto.NicknameDto;
import com.easyone.travelance.domain.member.dto.OneAccountDto;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberService memberService;
    
    // 추가정보 업데이트
    @Transactional
    public MemberDto.Response updateAdditionalInfo(String password, String nickname, String email) {
        Member member = memberService.findMemberByEmail(email);

        member.updateAdditionalInfo(nickname,password, Role.MEMBER);

        return MemberDto.Response.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
    }

    // 닉네임 업데이트
    @Transactional
    public NicknameDto updateNickname(Member member, String nickname){
        member.editNickname(nickname);

        return NicknameDto.of(member);
    }

    // 주 계좌 업데이트
    @Transactional
    public OneAccountDto updateOneAccount(MainAccount mainAccount, String account){
        mainAccount.setOneAccount(account);
        return OneAccountDto.of(mainAccount);
    }
}
