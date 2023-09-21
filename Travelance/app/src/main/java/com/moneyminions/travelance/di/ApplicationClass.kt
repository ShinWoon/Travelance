package com.moneyminions.travelance.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {

    companion object{
    }

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "db2ceb8eb8e28b57ce891b7f53534870")
    }
}