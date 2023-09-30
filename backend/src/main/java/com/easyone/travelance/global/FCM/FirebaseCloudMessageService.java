package com.easyone.travelance.global.FCM;

import com.easyone.travelance.domain.payment.entity.Payment;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/travelance-fada4/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body,String targetData) throws IOException {
        String message = makeMessage(targetToken, title, body, targetData);
        System.out.println("FCM sendMessageTo");

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body, String targetData) throws JsonProcessingException {
        System.out.println("FCM makeMessageTo");

        Payment payment = objectMapper.readValue(targetData, Payment.class);

        FcmMessage.Notification notification = FcmMessage.Notification.builder()
                .title(title)
                .body(body)
                .build();

        FcmMessage.Data data = FcmMessage.Data.builder()
                .paymentId(payment.getId())
                .paymentAmount(payment.getPaymentAmount())
                .content(payment.getPaymentContent())
                .build();

        FcmMessage.Message message = FcmMessage.Message.builder()
                .notification(notification)
                .data(data)
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
        InputStream is = getClass().getResourceAsStream("/travelance-fada4-firebase-adminsdk-66uyr-4c861b19d8");

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(is)
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        System.out.println("FIREBASE GET ACCESS TOKEN");
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}