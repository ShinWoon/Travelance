package com.moneyminions.travelance.kakaologin

import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSourceImpl
import com.moneyminions.presentation.utils.KakaoLogin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule{
    @Singleton
    @Provides
    fun provideKakaoLogin(preferenceDataSource: PreferenceDataSource): KakaoLogin {
        return KakaoLoginImpl(preferenceDataSource)
    }
}