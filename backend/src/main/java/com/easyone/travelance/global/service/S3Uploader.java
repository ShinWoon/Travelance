package com.easyone.travelance.global.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환 후 S3에 업로드
    @Transactional
    public String uploadFile(MultipartFile multipartFile, String dirName) throws Exception {
        if (multipartFile.getSize() != 0) {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("multipartFile -> File 전환 실패"));
            System.out.println("uploadFile");
            return upload(uploadFile, dirName);
        }
        return "null";
    }


    @Transactional
    public String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + "." + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 파일 삭제 (MultipartFile -> File 전환하며 로컬에 파일 생성됨)
        System.out.println("upload");
        return uploadImageUrl;  // 업로드 된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)  // PublicRead 권한으로 업로드 됨
        );
        System.out.println("putS3");
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()){
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        File dir = new File(System.getProperty("user.dir") + "/" + "s3local/");
        if (!dir.exists()){
            if(!dir.mkdirs()) {
                throw new IOException("폴더생성 실패" + dir.getAbsolutePath());
            }
        }

        File convertFile = new File(dir, now + ".jpg");

        if (convertFile.createNewFile()){
            try (FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            System.out.println("convert 성공");
            return Optional.of(convertFile);
        }
        System.out.println("convert 실패");
        return Optional.empty();
    }

}