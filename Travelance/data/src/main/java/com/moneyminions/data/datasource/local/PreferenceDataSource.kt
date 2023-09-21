package com.moneyminions.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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
        putString(X_ACCESS_TOKEN, jwtTokenDto.accessToken)
        putString(X_REFRESH_TOKEN, jwtTokenDto.refreshToken)
    }

    fun getJwtToken(): JwtTokenDto{
        return JwtTokenDto(
            getString(X_ACCESS_TOKEN,null)!!,
            getString(X_REFRESH_TOKEN,null)!!
        )
    }

    fun putRole(role: String){
        editor.putString(X_ROLE, role)
    }

}