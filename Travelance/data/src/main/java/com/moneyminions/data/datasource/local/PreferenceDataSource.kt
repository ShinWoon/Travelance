package com.moneyminions.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.moneyminions.domain.model.login.JwtTokenDto
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferenceDataSource D210"

class PreferenceDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object{
        private const val PREFERENCE_NAME = "travelance"
        private const val X_ACCESS_TOKEN = "access_token"
        private const val X_REFRESH_TOKEN = "refresh_token"
        private const val X_ROLE = "role"
        private const val FCM_TOKEN = "fcm_token"
    }

    private fun getPreference(context: Context): SharedPreferences{
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
    private val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }
    private val gson = Gson()

    private fun putString(key: String, data: String?){
        editor.putString(key, data)
        editor.apply()
    }

    private fun getString(key: String, defValue: String? = null): String? {
        return prefs.getString(key, defValue)
    }


    fun putJwtToken(jwtTokenDto: JwtTokenDto){
        if(jwtTokenDto.accessToken != null) putString(X_ACCESS_TOKEN, jwtTokenDto.accessToken)
        if(jwtTokenDto.refreshToken != null) putString(X_REFRESH_TOKEN, jwtTokenDto.refreshToken)
        if(jwtTokenDto.role != null) putString(X_ROLE, jwtTokenDto.role)
    }

    fun getJwtToken(): JwtTokenDto{
        return JwtTokenDto(
            getString(X_ACCESS_TOKEN),
            getString(X_REFRESH_TOKEN),
            getString(X_ROLE)
        )
    }

//    fun putRole(role: String?){
//        putString(X_ROLE, role)
//    }
//
//    fun getRole(): String{
//        return getString(X_ROLE) ?: "NONE"
//    }

    fun putFCMToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                // 토큰 요청 task가 실패 한 경우 처리
                return@addOnCompleteListener
            }
            // 토큰 요청 task가 성공한 경우 task의 result에 token 값이 내려온다.
            putString(FCM_TOKEN, it.result)
            Log.d(TAG, "initFirebase: ${getString(FCM_TOKEN)}")
        }
    }

    fun getFCMToken(): String{
        return getString(FCM_TOKEN)?: ""
    }

}