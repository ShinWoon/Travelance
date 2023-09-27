//package com.easyone.travelance.global.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.util.IOUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.io.*;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Optional;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class AwsS3Service {
//
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    private final AmazonS3Client amazonS3Client;
//
//    /**
//     * AWS S3에 이미지 파일 업로드
//     * @param multipartFile : 파일
//     * @param dirName : s3 버킷에서 만들어준 폴더 이름
//     * @return : Url
//     */
//    @Transactional
//    public Optional<String> upload(MultipartFile multipartFile, String dirName) throws Exception {
//
//        String fileName = createFileName(multipartFile.getOriginalFilename(), dirName);
//        if (multipartFile.getSize() != 0) {
//            File uploadFile = convert(multipartFile)
//                    .orElseThrow(() -> new IllegalArgumentException("multipartFile -> File 전환 실패"));
//            System.out.println("uploadFile");
//            return Optional.of(upload(uploadFile, dirName));
//        }
//        return Optional.empty();
//        // s3에 저장된 파일 url 얻어옴
//    }
//
//    // 파일 이름이 같으면 저장이 안되서 파일이름 앞에 UUID를 붙인다.
//    private String createFileName(String fileName, String dirName){
//        return dirName + "/" + UUID.randomUUID() + fileName;
//    }
//
//    @Transactional
//    public String upload(File uploadFile, String dirName) {
//        String fileName = dirName + "/" + UUID.randomUUID() + "." + uploadFile.getName();
//        String uploadImageUrl = putS3(uploadFile, fileName);
//
//        removeNewFile(uploadFile);  // 로컬에 생성된 파일 삭제 (MultipartFile -> File 전환하며 로컬에 파일 생성됨)
//        System.out.println("upload");
//        return uploadImageUrl;  // 업로드 된 파일의 S3 URL 주소 반환
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(
//                new PutObjectRequest(bucket, fileName, uploadFile)
//                        .withCannedAcl(CannedAccessControlList.PublicRead)  // PublicRead 권한으로 업로드 됨
//        );
//        System.out.println("putS3");
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    private void removeNewFile(File targetFile) {
//        if (targetFile.delete()){
//            log.info("파일이 삭제되었습니다.");
//        }else {
//            log.info("파일이 삭제되지 못했습니다");
//        }
//    }
//
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
//
//        File dir = new File(System.getProperty("user.dir") + "/" + "s3local/");
//        if (!dir.exists()){
//            if(!dir.mkdirs()) {
//                throw new IOException("폴더생성 실패" + dir.getAbsolutePath());
//            }
//        }
//
//        File convertFile = new File(dir, now + ".jpg");
//
//        if (convertFile.createNewFile()){
//            try (FileOutputStream fos = new FileOutputStream(convertFile)){
//                fos.write(file.getBytes());
//            }
//            System.out.println("convert 성공");
//            return Optional.of(convertFile);
//        }
//        System.out.println("convert 실패");
//        return Optional.empty();
//    }
//
//
////    public void delete(String imageUrl, String dirName) {
////        try {
////            String keyName = URLDecoder.decode(dirName + "/" + imageUrl.split("/")[2], StandardCharsets.UTF_8);
////            System.out.println(keyName);
////            boolean isFileExist = amazonS3.doesObjectExist(bucket, keyName);
////            if (isFileExist) {
////                amazonS3.deleteObject(bucket, keyName);
////            } else {
////                throw new IllegalArgumentException("해당 이미지 파일이 없습니다.");
////            }
////        } catch (Exception e){
////            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 파일 삭제를 실패하였습니다.");
////        }
////
////    }
//
//
//
//}
