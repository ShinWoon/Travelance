package com.easyone.travelance.global.FCM;

import com.easyone.travelance.domain.payment.entity.Payment;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMService{

    public void sendFCMNotification(String token, String title, String body, Long id) {
        try{

            Map<String, String> data = new HashMap<>();

            // 메시지
            data.put("title", title);
            data.put("message", body);
            data.put("paymentId", String.valueOf(id));

            // 알림 보낼 대상 설정
            Message message = Message.builder()
                    .putAllData(data)
                    .setToken(token)
                    .build();

            // FCM으로 알림 전송
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    public void registerPaid(String fcmToken, Payment payment){
        Long paymentId = payment.getId();
        Long paymentAmount = payment.getPaymentAmount();
        String content = payment.getPaymentContent();

        String title = "공금등록 알림";
        String body = String.format("%s,%s", paymentAmount, content);
//        String paymentId = String.format("%s", paymentId);

        log.info(title);
        log.info(body);

        this.sendFCMNotification(fcmToken, title, body, paymentId);
    }
    
    public void sendFcmComplete(String fcmToken, Long roomId){
        String title = "정산완료 알림";
        String body = String.format("%s", roomId);

        log.info(title);
        log.info(body);

        this.sendFCMNotification(fcmToken, title, body, 0L);
    }

}
