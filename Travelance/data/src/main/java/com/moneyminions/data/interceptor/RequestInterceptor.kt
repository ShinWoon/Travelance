package com.moneyminions.data.interceptor

import android.util.Log
import com.moneyminions.data.datasource.local.PreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.Exception

private const val TAG = "RequestInterceptor D210"
class RequestInterceptor(
    private val preferenceDataSource: PreferenceDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        try{
            preferenceDataSource.getString("access_token").let{ token ->
                token?.let {
                    builder.addHeader("Authorization", "Bearer $token")
                    Log.d(TAG, "intercept: JWT AccessToken 헤더에 담았습니다.")
                    return chain.proceed(builder.build())
                }
            }
        }catch (e: Exception){
            return chain.proceed(chain.request())
        }

        return chain.proceed(chain.request())
    }
}