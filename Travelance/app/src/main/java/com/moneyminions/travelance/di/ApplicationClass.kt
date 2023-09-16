package com.moneyminions.travelance.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "2c884c14ef5da98d71afb9875e8313f6")
    }
}