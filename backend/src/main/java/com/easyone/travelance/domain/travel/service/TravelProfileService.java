package com.easyone.travelance.domain.travel.service;

import com.easyone.travelance.domain.member.respository.ProfileRepository;
import com.easyone.travelance.domain.travel.entity.TravelRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class TravelProfileService {
    private final ProfileRepository profileRepository;


//    // 게시글 이미지 저장
//    public void saveImage(TravelRoom travelRoom, MultipartFile imageFile){
//        String imageUrl = awsS3Service.upload(imageFile, imageDirName).getPath();
//        ArticleImageRequestDto requestDto = ArticleImageRequestDto.builder()
//                .imageName(imageFile.getOriginalFilename())
//                .imageUrl(imageUrl)
//                .build();
//        ArticleImage articleImage = requestDto.toEntity(article);
//        articleImageRepository.save(articleImage);
//    }


}
