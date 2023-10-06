package com.moneyminions.paybank.service


import android.util.Log
import com.google.gson.Gson
import com.moneyminions.paybank.model.ErrorResponse
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

private const val TAG = "ResponseInterceptor D210"
class ResponseInterceptor(
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        var accessToken = ""
        var isRefreshable = false

        Log.d(TAG, "intercept: 지금 코드 ${response.code}")
        Log.d(TAG, "intercept: 지금 네트워크 리스폰스 ${response.networkResponse}")

        when (response.code) {
            400 -> {
                Log.d(TAG, "intercept: 에러 : 400 에러입니다.")
            }
            401 -> {
                val errorResponse = parseErrorResponse(response.body)
                Log.d(TAG, "intercept: 에러 바디 파싱 !!!!!!!!!! ${errorResponse}")
//                when(errorResponse.errorCode){
//
//                }
            }
            403 -> {
                Log.d(TAG, "intercept: 에러 : 403 에러입니다.")
                val errorResponse = parseErrorResponse(response.body)
                Log.d(TAG, "intercept: 에러 바디 파싱 !!!!!!!!!! ${errorResponse}")

                // 에러 코드로 분기
                when (errorResponse.errorCode) {
                    "Auth-009" -> {
                        Log.d(TAG, "intercept: 다시 로그인 해야합니다.")
                        throw (IOException("required_re_login"))
                    }
                }
            }
            404 -> {
                Log.d(TAG, "intercept: 에러 : 404 에러입니다.")
            }
            500 -> {
                Log.d(TAG, "intercept: 에러 : 500 에러입니다.")
            }
        }

        if(isRefreshable) {
            //TODO 헤더 갈아 끼우고 다시 요청
            Log.d(TAG, "intercept: 리프레시가 알맞게 통신했고, 새 엑세스토큰으로 가능하다는 소리입니다~")
            val newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
            return chain.proceed(newRequest)
        }

        return response
    }

    private fun parseErrorResponse(responseBody: ResponseBody?): ErrorResponse {
        val gson = Gson()
        return gson.fromJson(responseBody?.charStream(), ErrorResponse::class.java)
    }
}