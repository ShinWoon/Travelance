package com.easyone.travelance.extertnal.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

    private String id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {

        private String email;
        private Profile profile;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Profile {

            private String nickname;

            public Profile() {
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }

    // 기본 생성자 추가
    public KakaoUserInfoResponseDto() {}

    @Builder
    public KakaoUserInfoResponseDto(String id, KakaoAccount kakaoAccount) {
        this.id = id;
        this.kakaoAccount = kakaoAccount;
    }
}
