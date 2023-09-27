package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.travel.dto.UserProfileRequestDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.global.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RequiredArgsConstructor
@Service
public class TravelProfileService {
    private final ProfileRepository profileRepository;
    private final AwsS3Service awsS3Service;


    // 게시글 이미지 저장
    public void saveImage(TravelRoom travelRoom, MultipartFile imageFile, Member member){
        String imageUrl = awsS3Service.upload(imageFile, "profile").toString();
        UserProfileRequestDto requestDto = UserProfileRequestDto.builder()
                .imageName(imageFile.getOriginalFilename())
                .imageUrl(imageUrl)
                .build();

        Profile profile = requestDto.toEntity(travelRoom);
        profileRepository.save(profile);
    }


}
