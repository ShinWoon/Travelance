package com.moneyminions.paybank.service

import com.moneyminions.paybank.model.FcmTokenRequest
import com.moneyminions.paybank.model.NetworkResult
import com.moneyminions.paybank.model.PaymentRequest
import com.moneyminions.paybank.model.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BankService { //TODO SWAGGER 보고 함수 생성

    /**
     * 결제 했다는 api
     */
//    @POST("bank/card/payment/alert")
//    suspend fun

    /**
     * FCM 토큰 저장
     */
    @POST("bank/accounter/fcm")
    suspend fun postFcmToken(@Body fcmTokenRequest: FcmTokenRequest)

    /**
     * 결제
     */
    @POST("bank/card/payment/alert")
    suspend fun postPaymentRequest(@Body paymentRequest: PaymentRequest): NetworkResult<PaymentResponse>

}