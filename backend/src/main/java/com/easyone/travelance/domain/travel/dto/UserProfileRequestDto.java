package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileRequestDto {
    private String imageName;
    private String imageUrl;

    @Builder
    public UserProfileRequestDto(String imageName, String imageUrl) {
        this.imageName=imageName;
        this.imageUrl=imageUrl;
    }

    public Profile toEntity(TravelRoom travelRoom, Member member){
        return Profile.builder()
                .profileUrl(imageUrl)
                .ProfileName(imageName)
                .member(member)
                .travelRoom(travelRoom)
                .member(member)
                .build();
    }


}
