package com.easyone.travelance.extertnal.kakao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class KakaoTokenDto {

    // 토큰 요청시
    @Builder
    @Getter
    public static class Request{
        private String grant_type;
        private String client_id;
        private String client_secret;
        private String redirect_url;
        private String code;
    }



    // 토큰 응답 결과
    @Builder
    @Getter
    @ToString
    public static class Response {
        private String token_type;
        private String access_token;
        private Integer expires_in;
        private String refresh_token;
        private Integer refresh_token_expires_in;
        private String scope;
        private String id_token;

        public Response() {
            // 기본 생성자
        }

        @Builder
        public Response(String token_type, String access_token, Integer expires_in, String refresh_token, Integer refresh_token_expires_in, String scope, String id_token) {
            this.token_type = token_type;
            this.access_token = access_token;
            this.expires_in = expires_in;
            this.refresh_token = refresh_token;
            this.refresh_token_expires_in = refresh_token_expires_in;
            this.scope = scope;
            this.id_token = id_token;
        }
    }


}
