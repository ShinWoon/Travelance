package com.easyone.travelance.domain.member.controller;

import com.easyone.travelance.domain.member.dto.MemberDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final TokenService tokenService;
    private final MemberService memberService;
    private final MemberInfoService memberInfoService;
    // 추가정보 업데이트 메서드
    @Operation(summary = "추가 정보", description = "회원가입후 추가정보 관련 메서드입니다. \n\n " +"\n\n### [ 수행절차 ]\n\n"+"- login 에서 발급 받은 access-token을 자물쇠에 넣고 Execute 해주세요. (request body는 아래에 예시값의 request값만 사용해주세요.)\n\n"+ "- Response body의 accessToken 또는 Response headers의 newtoken을 복사하여 새로 자물쇠에 넣어 주세요 \n\n")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "#### 성공"), @ApiResponse(responseCode = "에러", description = "#### 에러 이유를 확인 하십시오", content =@Content(schema = @Schema(implementation = ErrorResponse.class), examples = { @ExampleObject(name="400_User-003", value ="이미 등록된 닉네임입니다. 다른 닉네임을 기입해 주세요"), @ExampleObject( name = "401_Auth-001", value = "토큰이 만료되었습니다. 토큰을 재발급 받아주세요"), @ExampleObject( name = "401_Auth-005", value = "해당 토큰은 유효한 토큰이 아닙니다. 토큰값이 추가정보 기입에서 받은 new token 값이 맞는지 확인해주세요"), @ExampleObject( name = "401_Auth-006", value = "Authorization Header가 없습니다. 자물쇠에 access token값을 넣어주세요."), @ExampleObject( name = "500", value = "서버에러")}))})
    @PatchMapping("/additional")
    public ResponseEntity<AccessTokenResponseDto> updateAdditionalInfo(@RequestBody MemberDto.UpdateRequest request, @MemberInfo MemberInfoDto memberInfoDto) {

        MemberDto.Response response = memberInfoService.updateAdditionalInfo(request, memberInfoDto.getEmail());

        // 이메일로 멤버 찾아서 refresh token 가져오기
        String refreshToken = memberService.findMemberByEmail(memberInfoDto.getEmail()).getMemberAuth().getRefreshToken();
        // 엑세스 토큰 재발급
        AccessTokenResponseDto newToken = tokenService.createAccessTokenByRefreshToken(refreshToken);

        // 본문에는 response, 헤더에는 새 토큰을 추가하여 ResponseEntity 생성
        return ResponseEntity.ok()
                .header("newToken", newToken.getAccessToken())
                .body(newToken);
    }
}
