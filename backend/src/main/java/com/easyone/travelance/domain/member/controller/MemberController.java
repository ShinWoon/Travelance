package com.easyone.travelance.domain.member.controller;


import com.easyone.travelance.domain.account.dto.SelectedAccountRequestDto;
import com.easyone.travelance.domain.account.entity.Account;
import com.easyone.travelance.domain.account.service.AccountService;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import com.easyone.travelance.domain.card.service.CardService;
import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.dto.request.AdditionalRequestDto;
import com.easyone.travelance.domain.member.dto.NicknameDto;
import com.easyone.travelance.domain.member.dto.OneAccountDto;
import com.easyone.travelance.domain.member.dto.response.MypageResponseDto;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberInfoService;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.token.dto.AccessTokenResponseDto;
import com.easyone.travelance.domain.token.service.TokenService;
import com.easyone.travelance.global.error.ErrorResponse;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final TokenService tokenService;
    private final MemberService memberService;
    private final MemberInfoService memberInfoService;
    private final AccountService accountService;
    private final CardService cardService;

    // 닉네임 수정
    @Operation(summary = "닉네임 수정", description = "닉네임 수정 관련 메서드입니다.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "#### 성공"), @ApiResponse(responseCode = "에러", description = "#### 에러 이유를 확인 하십시오", content =@Content(schema = @Schema(implementation = ErrorResponse.class), examples = { @ExampleObject(name="400_User-003", value ="이미 등록된 닉네임입니다. 다른 닉네임을 기입해 주세요"), @ExampleObject( name = "401_Auth-001", value = "토큰이 만료되었습니다. 토큰을 재발급 받아주세요"), @ExampleObject( name = "401_Auth-005", value = "해당 토큰은 유효한 토큰이 아닙니다. 토큰값이 추가정보 기입에서 받은 new token 값이 맞는지 확인해주세요"), @ExampleObject( name = "401_Auth-006", value = "Authorization Header가 없습니다. 자물쇠에 access token값을 넣어주세요."), @ExampleObject( name = "500", value = "서버에러")}))})
    @PatchMapping("/update/nickname")
    public ResponseEntity<NicknameDto> updateNickname(@MemberInfo MemberInfoDto memberInfoDto, @RequestParam String nickname){

        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        NicknameDto nicknameResponseDto = memberInfoService.updateNickname(member,nickname);

        return ResponseEntity.ok(nicknameResponseDto);
    }

    //마이페이지
    @Operation(summary = "마이페이지 조회", description = "나의 메이페이지를 조회하는 메서드입니다.")
    @PostMapping("/mypage")
    public ResponseEntity<MypageResponseDto> myPage(@MemberInfo MemberInfoDto memberInfoDto){
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        List<SelectedAccountRequestDto> accounts = memberService.findAllAccountsForMember(member);
        List<SelectedCardRequestDto> cards = memberService.findAllCardsForMember(member);
        log.info("cards : " + cards);
        MypageResponseDto mypageResponseDto = new MypageResponseDto();
        mypageResponseDto.setAccountList(accounts);
        mypageResponseDto.setCardList(cards);
        mypageResponseDto.setNickname(member.getNickname());
        mypageResponseDto.setPassword(member.getPassword());

        return ResponseEntity.ok(mypageResponseDto);

    }

    // 계좌, 카드 추가 등록
    @Operation(summary = "계좌, 카드 추가 등록", description = "나의 계좌, 카드를 추가 등록하는 메서드입니다. \n\n " +"\n\n### [ 수행절차 ]\n\n"+"- login 에서 발급 받은 access-token을 자물쇠에 넣고 Execute 해주세요. (request body는 아래에 예시값의 request값만 사용해주세요.)\n\n"+ "- Response body의 accessToken 또는 Response headers의 newtoken을 복사하여 새로 자물쇠에 넣어 주세요 \n\n" + "### [DTO 양식] \n\n" +
            "```\n\n{\n\n" +
            "    \"accountList\": [\n" +
            "        {\n\n" +
            "        \"account\": \"6666666666666666\",\n" +
            "        \"bankName\": \"대구\",\n" +
            "        \"idx\": 4\n" +
            "        },\n\n" +
            "        {\n\n" +
            "        \"account\": \"7753621811018015\",\n" +
            "        \"bankName\": \"SC제일\",\n" +
            "        \"idx\": 0\n" +
            "        }\n\n" +
            "    ],\n\n" +
            "    \"cardList\": [\n" +
            "        {\n\n" +
            "        \"cardNumber\": \"4215154824392854\",\n" +
            "        \"cardCoName\": \"KB국민\",\n" +
            "        \"idx\": 1\n" +
            "        },\n\n" +
            "        {\n\n" +
            "        \"cardNumber\": \"3737467972008765\",\n" +
            "        \"cardCoName\": \"KB국민\",\n" +
            "        \"idx\": 1\n" +
            "        }\n\n" +
            "    ],\n\n" +
            "    \"password\": \"아무거나\",\n\n" +
            "    \"nickname\": \"아무거나\"\n\n" +
            "    }\n\n```\n\n" + "이러한 형식입니다")
    @PostMapping("/add")
    public ResponseEntity<ResultDto> addCardAccount(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody AdditionalRequestDto addRequestDto){

        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        // 계좌 등록 로직
        List<SelectedAccountRequestDto> selectedAccountRequestDtoList = addRequestDto.getAccountList();

        MainAccount mainAccount = member.getMainAccount();
        for (SelectedAccountRequestDto selectedAccountRequestDto : selectedAccountRequestDtoList) {
            accountService.SaveAccount(member, mainAccount, selectedAccountRequestDto);
        }

        // 카드 등록 로직
        List<SelectedCardRequestDto> selectedCardRequestDtoList = addRequestDto.getCardList();

        for (SelectedCardRequestDto selectedCardRequestDto : selectedCardRequestDtoList) {
            cardService.SaveCard(member,selectedCardRequestDto);
        }

        return new ResponseEntity<>(new ResultDto("추가 성공"), HttpStatus.OK);
    }

    // 추가정보 업데이트 메서드
    @Operation(summary = "추가정보 입력", description = "회원가입후 추가 정보 관련 메서드입니다. \n\n " +"\n\n### [ 수행절차 ]\n\n"+"- login 에서 발급 받은 access-token을 자물쇠에 넣고 Execute 해주세요. (request body는 아래에 예시값의 request값만 사용해주세요.)\n\n"+ "- Response body의 accessToken 또는 Response headers의 newtoken을 복사하여 새로 자물쇠에 넣어 주세요 \n\n" + "### [DTO 양식] \n\n" +
            "```\n\n{\n\n" +
            "    \"accountList\": [\n" +
            "        {\n\n" +
            "        \"account\": \"6666666666666666\",\n" +
            "        \"bankName\": \"대구\",\n" +
            "        \"idx\": 4\n" +
            "        },\n\n" +
            "        {\n\n" +
            "        \"account\": \"7753621811018015\",\n" +
            "        \"bankName\": \"SC제일\",\n" +
            "        \"idx\": 0\n" +
            "        }\n\n" +
            "    ],\n\n" +
            "    \"cardList\": [\n" +
            "        {\n\n" +
            "        \"cardNumber\": \"4215154824392854\",\n" +
            "        \"cardCoName\": \"KB국민\",\n" +
            "        \"idx\": 1\n" +
            "        },\n\n" +
            "        {\n\n" +
            "        \"cardNumber\": \"3737467972008765\",\n" +
            "        \"cardCoName\": \"KB국민\",\n" +
            "        \"idx\": 1\n" +
            "        }\n\n" +
            "    ],\n\n" +
            "    \"password\": \"1234\",\n\n" +
            "    \"nickname\": \"1234\"\n\n" +
            "    }\n\n```\n\n" + "이러한 형식입니다")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "#### 성공"), @ApiResponse(responseCode = "에러", description = "#### 에러 이유를 확인 하십시오", content =@Content(schema = @Schema(implementation = ErrorResponse.class), examples = { @ExampleObject(name="400_User-003", value ="이미 등록된 닉네임입니다. 다른 닉네임을 기입해 주세요"), @ExampleObject( name = "401_Auth-001", value = "토큰이 만료되었습니다. 토큰을 재발급 받아주세요"), @ExampleObject( name = "401_Auth-005", value = "해당 토큰은 유효한 토큰이 아닙니다. 토큰값이 추가정보 기입에서 받은 new token 값이 맞는지 확인해주세요"), @ExampleObject( name = "401_Auth-006", value = "Authorization Header가 없습니다. 자물쇠에 access token값을 넣어주세요."), @ExampleObject( name = "500", value = "서버에러")}))})
    @PatchMapping("/additional")
    public ResponseEntity<AccessTokenResponseDto> updateAdditionalInfo(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody AdditionalRequestDto additionalRequestDto) {

        memberInfoService.updateAdditionalInfo(additionalRequestDto.getPassword(), additionalRequestDto.getNickname(),memberInfoDto.getEmail());

        // 이메일로 멤버 찾아서 refresh token 가져오기
        String refreshToken = memberService.findMemberByEmail(memberInfoDto.getEmail()).getRefreshToken();
        // 엑세스 토큰 재발급
        AccessTokenResponseDto newToken = tokenService.createAccessTokenByRefreshToken(refreshToken);

        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        
        
        // 계좌 등록 로직
        List<SelectedAccountRequestDto> selectedAccountRequestDtoList = additionalRequestDto.getAccountList();
        
        // 주 계좌 등록
        member.getMainAccount().setOneAccount(selectedAccountRequestDtoList.get(0).getAccount());

        MainAccount mainAccount = member.getMainAccount();
//        log.warn("mainAccount : " + mainAccount);
//        log.warn("Received JSON data: " + selectedAccountRequestDtoList);
        for (SelectedAccountRequestDto selectedAccountRequestDto : selectedAccountRequestDtoList) {
            accountService.SaveAccount(member, mainAccount, selectedAccountRequestDto);
        }
        
        // 카드 등록 로직
        List<SelectedCardRequestDto> selectedCardRequestDtoList = additionalRequestDto.getCardList();

        for (SelectedCardRequestDto selectedCardRequestDto : selectedCardRequestDtoList) {
            cardService.SaveCard(member,selectedCardRequestDto);
        }


        // 본문에는 response, 헤더에는 새 토큰을 추가하여 ResponseEntity 생성
        return ResponseEntity.ok()
                .header("newToken", newToken.getAccessToken())
                .body(newToken);
    }


    @Operation(summary = "내가 등록한 계좌 조회", description = "내가 등록한 계좌를 조회하는 메서드입니다.")
    @PostMapping("/search/myaccount")
    public ResponseEntity<List<SelectedAccountRequestDto>> myAccount(@MemberInfo MemberInfoDto memberInfoDto){
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        List<SelectedAccountRequestDto> accounts = memberService.findAllAccountsForMember(member);
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary = "내가 등록한 카드 조회", description = "내가 등록한 계좌를 조회하는 메서드입니다.")
    @PostMapping("/search/mycard")
    public ResponseEntity<List<SelectedCardRequestDto>> myCard(@MemberInfo MemberInfoDto memberInfoDto){
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        List<SelectedCardRequestDto> accounts = memberService.findAllCardsForMember(member);
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary = "내 주계좌 변경", description = "나의 주 계좌 번호를 변경하는 메서드입니다.")
    @PatchMapping("/update/oneaccount")
    public ResponseEntity<OneAccountDto> updateOneAccount(@MemberInfo MemberInfoDto memberInfoDto, @RequestParam String account){

        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        List<Account> accountList = member.getMainAccount().getAccountList();
        log.warn("accountList : " + accountList);

        boolean accountExists = accountList.stream()
                .anyMatch(a -> a.getAccount().equals(account));

        if (accountExists) {
            OneAccountDto oneAccountDto = memberInfoService.updateOneAccount(member.getMainAccount(), account);
            return ResponseEntity.ok(oneAccountDto);
        } else {
            throw new RuntimeException("본인 계좌 리스트에 존재하지 않는 계좌번호입니다.");
        }


    }

    @Operation(summary = "회원탈퇴",description = "현재 로그인한 유저의 회원탈퇴 메서드입니다.")
    @DeleteMapping("/delete")
    public ResponseEntity<ResultDto> deleteMember(@MemberInfo MemberInfoDto memberInfoDto) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        memberService.deleteMember(member.getPrivateId());
        return ResponseEntity.ok(new ResultDto("회원탈퇴 완료"));
    }

}
