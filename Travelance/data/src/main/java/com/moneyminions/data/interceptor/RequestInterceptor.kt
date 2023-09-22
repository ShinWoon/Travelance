package com.moneyminions.data.interceptor

import android.util.Log
import com.moneyminions.data.datasource.local.PreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.log

private const val TAG = "RequestInterceptor D210"
class RequestInterceptor @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        Log.d(TAG, "intercept.... ${preferenceDataSource.getJwtToken()}")
        try{
            preferenceDataSource.getJwtToken().let{ token ->
                token?.let {
                    builder.addHeader("Authorization", "Bearer ${token.accessToken}")
                    Log.d(TAG, "intercept: JWT AccessToken 헤더에 담았습니다. ${token.accessToken}")
//                    Log.d(TAG, "intercept: body. ${builder.}")
                    return chain.proceed(builder.build())
                }
            }
        }catch (e: Exception){
            Log.d(TAG, "intercept: $e")
            return chain.proceed(chain.request())
        }
    }
}