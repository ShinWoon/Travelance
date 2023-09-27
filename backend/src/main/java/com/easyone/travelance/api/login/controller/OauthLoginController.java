package com.easyone.travelance.api.login.controller;


import com.easyone.travelance.api.login.dto.OauthLoginDto;
import com.easyone.travelance.api.login.service.OauthLoginService;
import com.easyone.travelance.api.login.validator.OauthValidator;
import com.easyone.travelance.domain.member.constant.SocialType;
import com.easyone.travelance.global.error.ErrorResponse;
import com.easyone.travelance.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Tag(name = "Auth", description = "소셜로그인 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthLoginController {

    private final OauthValidator oauthValidator;
    private final OauthLoginService oauthLoginService;

    @Operation(summary = "로그인", description = "로그인 및 회원가입을 수행합니다.\n\n"+"\n\n### [ 수행절차 ]\n\n"+"- Kakao, Naver 에서 발급 받은 access-token을 자물쇠에 넣고 Execute 해주세요. (아래 예시값 그대로 Execute 해도 괜찮습니다.)\n\n"+ "- NAVER : 소셜타입은 대문자로 써서 요청해주세요  \n\n" + "- Execute 으로 발급 받은 access-token을 복사하여 memeber 회원관련 API의 추가정보로 이동합니다.\n\n")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "#### 성공"), @ApiResponse(responseCode = "에러", description = "#### 에러 이유를 확인 하십시오", content =@Content(schema = @Schema(implementation = ErrorResponse.class), examples = {@ExampleObject(name="400_User-001", value ="잘못된 로그인 타입 입니다. KAKAO 또는 NAVER로 작성해주세요."), @ExampleObject(name="400_User-002", value ="이미 등록된 회원입니다."), @ExampleObject( name = "401_Auth-006", value = "Authorization Header가 없습니다. 자물쇠에 access token값을 넣어주세요."), @ExampleObject( name = "500", value = "서버에러") }))})
    @PostMapping("/login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(@RequestBody OauthLoginDto.Request oauthLoginRequestDto,
                                                             HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
        oauthValidator.validateSocialType(oauthLoginRequestDto.getSocialType());

        String accessToken = authorizationHeader.split(" ")[1]; // 소셜에서 받은 accesstoken
        String firebaseToken = oauthLoginRequestDto.getFirebaseToken(); // Firebase 토큰 값
        OauthLoginDto.Response jwtResponseDto = oauthLoginService.oauthLogin(accessToken,
                SocialType.from(oauthLoginRequestDto.getSocialType()),firebaseToken);

        return ResponseEntity.ok(jwtResponseDto);
    }

}
