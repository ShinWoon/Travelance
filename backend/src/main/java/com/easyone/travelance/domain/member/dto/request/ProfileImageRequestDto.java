package com.easyone.travelance.domain.member.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileImageRequestDto {
    private String imageUrl;

    @Builder
    public ProfileImageRequestDto(String imageUrl) {
        this.imageUrl=imageUrl;
    }
}
