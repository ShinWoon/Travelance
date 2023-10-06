package com.moneyminions.data.interceptor

import android.util.Log
import com.google.gson.Gson
import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.model.error.response.ErrorResponse
import com.moneyminions.data.service.BusinessService
import com.moneyminions.data.utils.Constants.BASE_URL
import com.moneyminions.domain.model.login.JwtTokenDto
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val TAG = "ResponseInterceptor D210"
class ResponseInterceptor(
    val preferenceDataSource: PreferenceDataSource
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

                    when(errorResponse.errorCode){
                        "Auth-001" -> { // 엑세스 토큰 만료 신호
                            Log.d(TAG, "intercept: 에러(Auth-001) : 만료된 토큰입니다.")
                            runBlocking {
                                //토큰 갱신 api 호출

                                Log.d(TAG, "intercept: ${preferenceDataSource.getJwtToken().refreshToken})}")
                                preferenceDataSource.getJwtToken()?.let {
                                    Log.d(TAG, "intercept: ${preferenceDataSource.getJwtToken().refreshToken}")

                                    val result = Retrofit.Builder()
                                        .baseUrl(BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                        .create(BusinessService::class.java).postReAccessToken("Bearer ${it.refreshToken}")

                                    Log.d(
                                        TAG, "intercept 현재 찐 refresh: ${
                                            preferenceDataSource.getJwtToken().refreshToken
                                        }"
                                    )
                                    if (result.isSuccessful) {
                                        Log.d(TAG, "intercept: 다시 받아오는데 성공했어요!!!!!!")
                                        preferenceDataSource.putJwtToken(JwtTokenDto(result.body()!!.accessToken,null,result.body()!!.role))
                                        Log.d(TAG, "intercept: 만료된 토큰 다시 받은거 ${result.body()!!.accessToken}")
                                        Log.d(TAG, "갈아끼운 jwtToken 정보 ${preferenceDataSource.getJwtToken()}")
                                        accessToken = result.body()!!.accessToken
                                        isRefreshable = true
                                    }
                                    if (result.body() == null) {
                                        Log.d(TAG, "intercept: 리프레시 토큰으로 다시 받아오는 코드 실패입니다.")
//                                    Log.d(TAG, "intercept success : ${result.isSuccessful}")
//                                    Log.d(TAG, "intercept  : ${result.code()}")
//                                    Log.d(TAG, "intercept: ${result.headers()}")
//                                    Log.d(TAG, "intercept: ${result.message()}")
//                                    Log.d(TAG, "intercept: ${result.errorBody()}")
                                        throw (IOException("refresh_exception"))
                                    }
                                }
                            }
                        }
                        "Auth-006" -> { // 엑세스 토큰 invalid 신호
                            Log.d(TAG, "intercept: 에러(Auth-006) : 헤더에 토큰이 없습니다.")
                        }

                    }
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