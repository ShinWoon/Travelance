package com.easyone.travelance.global.FCM;

import com.easyone.travelance.domain.payment.entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/travelance-fada4/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body, Payment payment) throws IOException {
        String message = "";
        if (payment == null){
            message = makeMessage2(targetToken, title, body);
            log.warn("FCM 전송 완료");
            System.out.println("FCM sendMessageTo" + message);
        }else{
            message = makeMessage(targetToken, title, body, payment);
            System.out.println("FCM sendMessageTo" + message);
        }

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
        log.warn("FCM 메세지 : " + response);
        log.warn("FCM 요청메세지 : "+requestBody);
    }

    private String makeMessage(String targetToken, String title, String body, Payment payment) throws JsonProcessingException {
        System.out.println("FCM makeMessageTo");

        // 여기에서 모든 값들을 문자열로 변환
        FcmMessage.Data data = FcmMessage.Data.builder()
                .paymentId(String.valueOf(payment.getId()))
                .paymentAmount(String.valueOf(payment.getPaymentAmount()))
                .content(payment.getPaymentContent())
                .build();

        FcmMessage.Message message = FcmMessage.Message.builder()
                .data(data)
                .token(targetToken)
                .build();

        FcmMessage fcmMessage = FcmMessage.builder()
                .validateOnly(false)
                .message(message)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String makeMessage2(String targetToken, String title, String body) throws JsonProcessingException {
        System.out.println("FCM makeMessageTo2");

        FcmMessage.Notification notification = FcmMessage.Notification.builder()
                .title(title)
                .body(body)
                .build();

        FcmMessage.Message message = FcmMessage.Message.builder()
                .notification(notification)
                .token(targetToken)
                .build();

        FcmMessage fcmMessage = FcmMessage.builder()
                .validateOnly(false)
                .message(message)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        // 클래스패스 내의 리소스로 파일 로드
        InputStream is = getClass().getResourceAsStream("/FCM/travelance-fada4-firebase-adminsdk-66uyr-4c861b19d8.json");

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(is)
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        System.out.println("FIREBASE GET ACCESS TOKEN");
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}