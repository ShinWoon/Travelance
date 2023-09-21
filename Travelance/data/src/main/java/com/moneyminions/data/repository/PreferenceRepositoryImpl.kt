package com.moneyminions.data.repository

import android.util.Log
import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.repository.PreferenceRepository
import javax.inject.Inject

private const val TAG = "PreferenceRepositoryImp D210"
class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
): PreferenceRepository {
    override fun putJwtToken(jwtTokenDto: JwtTokenDto) {
        Log.d(TAG, "putJwtToken: $preferenceDataSource ")
        preferenceDataSource.putJwtToken(jwtTokenDto)
    }

    override fun putRole(role: String) {
        preferenceDataSource.putRole(role)
    }


}