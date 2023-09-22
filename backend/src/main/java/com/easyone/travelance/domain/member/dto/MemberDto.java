package com.easyone.travelance.domain.member.dto;


import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.domain.member.constant.SocialType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private String email;
    private String nickname;


    @Builder
    public MemberDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;


    }


    @Getter
    @Schema(description = "예시 DTO")
    public static class UpdateRequest {
        @Schema(example = "rich_guy")
        private String nickname;
    }

    @Getter
    @Builder
    public static class Response {
        private String email;
        private String nickname;
        private SocialType socialType;
        private String password;
        private Role role;

    }
}
