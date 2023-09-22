package com.moneyminions.paybank.service

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

private const val TAG = "RequestInterceptor D210"
class RequestInterceptor @Inject constructor(
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        try{
            builder.addHeader("Authorization", "Bearer ssafy_d210_bankserver")
            Log.d(TAG, "intercept Header가 담겼습니다.")
        }catch (e: Exception){
            Log.d(TAG, "intercept: $e")
            return chain.proceed(chain.request())
        }
        return chain.proceed(chain.request())
    }
}