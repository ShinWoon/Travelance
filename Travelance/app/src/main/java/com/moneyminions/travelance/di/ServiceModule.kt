package com.moneyminions.travelance.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.interceptor.RequestInterceptor
import com.moneyminions.data.interceptor.ResponseInterceptor
import com.moneyminions.data.service.BusinessService
import com.moneyminions.data.service.example.ExampleService
import com.moneyminions.data.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

//    @Singleton
//    @Provides
//    @Named("BASE_URL")
//    fun BaseUrl() : String = "https://api.github.com"
    @Singleton
    @Provides
    @Named("BASE_URL")
    fun BaseUrl() : String = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(preferenceDataSource: PreferenceDataSource): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor(ResponseInterceptor(preferenceDataSource))
        .addInterceptor(RequestInterceptor(preferenceDataSource))
//            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
//            .addInterceptor(AddCookiesInterceptor())  //쿠키 전송
//            .addInterceptor(ReceivedCookiesInterceptor()) //쿠키 추출
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
//    }

    @Singleton
    @Provides
    fun provideRetrofit(
        preferenceDataSource: PreferenceDataSource,
        @Named("BASE_URL") baseUrl: String,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create(),
            ),
        )
        .client(provideOkHttpClient(preferenceDataSource))
        .build()

    @Singleton
    @Provides
    fun provideExampleService(
        retrofit: Retrofit,
    ): ExampleService = retrofit.create(ExampleService::class.java)

    @Singleton
    @Provides
    fun provideBusinessService(
        retrofit: Retrofit,
    ): BusinessService = retrofit.create(BusinessService::class.java)
}
