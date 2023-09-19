package com.moneyminions.travelance.kakaologin

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.presentation.utils.KakaoLogin
import com.moneyminions.travelance.di.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.moneyminions.travelance.di.ApplicationClass.Companion.X_REFRESH_TOKEN
import javax.inject.Inject

private const val TAG = "KakaoLoginImpl D210"
class KakaoLoginImpl @Inject constructor(
    preferenceDataSource: PreferenceDataSource
): KakaoLogin {
    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when{
            error != null -> Log.e(TAG, "kakao login error 2 $error")
            token != null -> {
                Log.d(TAG, "kakao login success -> access Token : ${token.accessToken}")
                Log.d(TAG, "kakao login success -> refresh Token : ${token.refreshToken}")
                //여기서 이 TOKEN을 가지고 ACCESS, REFRESH TOKEN 얻는 API 호출해야 함.
                preferenceDataSource.putString(X_ACCESS_TOKEN, token.accessToken)
                preferenceDataSource.putString(X_REFRESH_TOKEN, token.refreshToken)
                Log.d(TAG, "preference -> access Token : ${preferenceDataSource.getString(X_ACCESS_TOKEN)}")
                Log.d(TAG, "preference -> refresh Token : ${preferenceDataSource.getString(X_REFRESH_TOKEN)}")
            }
        }
    }

    override fun singInKakao(context: Context) {
        Log.d(TAG, "singInKakao....")
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)){
            //카카오톡 설치 시
            UserApiClient.instance.loginWithKakaoTalk(context){ token, error ->
                if(error != null){
                    Log.e(TAG, "kakao login error 1")
                }
                if(error is ClientError && error.reason == ClientErrorCause.Cancelled){
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(
                    context = context,
                    callback = kakaoCallback
                )
            }
        }else{
            //미설치 시
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = kakaoCallback
            )
        }
    }
}