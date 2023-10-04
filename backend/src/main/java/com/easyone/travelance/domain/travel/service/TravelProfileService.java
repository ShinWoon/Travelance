package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.entity.Profile;
import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.travel.dto.UserProfileRequestDto;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import com.easyone.travelance.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RequiredArgsConstructor
@Service
@Slf4j
public class TravelProfileService {
    private final ProfileRepository profileRepository;
    private final S3Uploader awsS3Service;


    // 게시글 이미지 저장
    public void saveImage(TravelRoom travelRoom, MultipartFile imageFile, Member member) throws Exception {
        String imageUrl = awsS3Service.uploadFile(imageFile, "profile");
        UserProfileRequestDto requestDto = UserProfileRequestDto.builder()
                .imageName(imageFile.getOriginalFilename())
                .imageUrl(imageUrl)
                .build();

        Profile profile = requestDto.toEntity(travelRoom, member);
        log.info(profile.getProfileUrl());
        profileRepository.save(profile);
    }


}
