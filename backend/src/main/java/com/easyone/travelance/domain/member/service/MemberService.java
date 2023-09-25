package com.easyone.travelance.domain.member.service;


import com.easyone.travelance.domain.account.dto.SelectedAccountRequestDto;
import com.easyone.travelance.domain.account.entity.Account;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import com.easyone.travelance.domain.card.entity.Card;
import com.easyone.travelance.domain.member.dto.MyAccountDto;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
//import com.easyone.travelance.domain.member.entity.MemberAuth;
//import com.easyone.travelance.domain.member.respository.MemberAuthRepository;
import com.easyone.travelance.domain.member.respository.MainAccountRepository;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.global.error.ErrorCode;
import com.easyone.travelance.global.error.exception.AuthenticationException;
import com.easyone.travelance.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MainAccountRepository mainAccountRepository;

    public Member registerMember(Member member) {
        validateDuplicateMember(member);

        // MainAccount 생성
        MainAccount mainAccount = MainAccount.builder()
                .oneAccount("some_account_number") // 원하는 계좌번호를 설정하세요.
                .member(member)
                .build();
        mainAccountRepository.save(mainAccount);

        // Member에 MainAccount 연결
        member.setMainAccount(mainAccount);

        return memberRepository.save(member);
    }

    public void updateMember(Member updatedMember) {
        Long memberId = updatedMember.getId();
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));

//        // 업데이트할 필드가 있다면 각 필드를 새로운 값으로 업데이트
//        if (updatedMember.getFirebaseToken() != null) {
//            existingMember.setFirebaseToken(updatedMember.getFirebaseToken());
//        }

        // 회원 정보 업데이트
        memberRepository.save(existingMember);
    }

    // 중복 검증
    private void validateDuplicateMember(Member member) throws BusinessException {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember.isPresent()){
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Member findMemberByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();
        if(tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return member;
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_EXISTS));
    }

    public List<SelectedAccountRequestDto> findAllAccountsForMember(Member member) {
        MainAccount mainAccount = member.getMainAccount();
        List<Account> accounts = mainAccount.getAccountList();

        List<SelectedAccountRequestDto> accountDtos = new ArrayList<>();
        for (Account account : accounts) {
            SelectedAccountRequestDto accountDto = new SelectedAccountRequestDto();
            accountDto.setAccount(account.getAccount());
            accountDto.setBankName(account.getAccountName());
            accountDto.setIdx(account.getIdx());
            accountDtos.add(accountDto);
        }

        return accountDtos;
    }

    public List<SelectedCardRequestDto> findAllCardsForMember(Member member) {
        List<Card> cards = member.getCardList();
        log.info("cards : " + cards);
        List<SelectedCardRequestDto> cardDtos = new ArrayList<>();
        for (Card card : cards){
            SelectedCardRequestDto cardDto = new SelectedCardRequestDto();
            cardDto.setCardNumber(card.getCardNumber());
            cardDto.setCardCoName(card.getCardCoName());
            cardDto.setIdx(card.getIdx());
            cardDtos.add(cardDto);


        }
        return cardDtos;
    }

}
