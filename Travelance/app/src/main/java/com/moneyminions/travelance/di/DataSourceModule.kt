package com.moneyminions.travelance.di

import com.moneyminions.data.datasource.remote.example.ExampleDataSource
import com.moneyminions.data.datasource.remote.example.ExampleDataSourceImpl
import com.moneyminions.data.datasource.remote.home.HomeDataSource
import com.moneyminions.data.datasource.remote.home.HomeDataSourceImpl
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSourceImpl
import com.moneyminions.data.datasource.remote.mypage.MyPageDataSource
import com.moneyminions.data.datasource.remote.mypage.MyPageDataSourceImpl
import com.moneyminions.data.datasource.remote.traveldetail.TravelDetailDataSource
import com.moneyminions.data.datasource.remote.traveldetail.TravelDetailDataSourceImpl
import com.moneyminions.data.datasource.remote.traveldone.TravelDoneDataSource
import com.moneyminions.data.datasource.remote.traveldone.TravelDoneDataSourceImpl
import com.moneyminions.data.datasource.remote.traveledit.TravelEditDataSource
import com.moneyminions.data.datasource.remote.traveledit.TravelEditDataSourceImpl
import com.moneyminions.data.datasource.remote.travellist.TravelListDataSource
import com.moneyminions.data.datasource.remote.travellist.TravelListDataSourceImpl
import com.moneyminions.data.datasource.remote.travelmap.TravelMapDataSource
import com.moneyminions.data.datasource.remote.travelmap.TravelMapDataSourceImpl
import com.moneyminions.data.service.BusinessService
import com.moneyminions.data.service.example.ExampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideExampleDataSource(exampleService: ExampleService): ExampleDataSource {
        return ExampleDataSourceImpl(exampleService)
    }

    @Singleton
    @Provides
    fun provideLoginDataSource(businessService: BusinessService): LoginDataSource {
        return LoginDataSourceImpl(businessService)
    }
    
    @Singleton
    @Provides
    fun provideTravelListDataSource(businessService: BusinessService): TravelListDataSource {
        return TravelListDataSourceImpl(businessService)
    }

    @Singleton
    @Provides
    fun provideMyPageDataSource(businessService: BusinessService): MyPageDataSource {
        return MyPageDataSourceImpl(businessService)
    }

    @Singleton
    @Provides
    fun provideTravelDetailDataSource(businessService: BusinessService): TravelDetailDataSource {
        return TravelDetailDataSourceImpl(businessService)
    }
    
    @Singleton
    @Provides
    fun provideHomeDataSource(businessService: BusinessService): HomeDataSource {
        return HomeDataSourceImpl(businessService)
    }

    @Singleton
    @Provides
    fun provideTravelEditDataSource(businessService: BusinessService): TravelEditDataSource {
        return TravelEditDataSourceImpl(businessService)
    }

    @Singleton
    @Provides
    fun provideTravelMapDataSource(businessService: BusinessService): TravelMapDataSource {
        return TravelMapDataSourceImpl(businessService)
    }

    @Singleton
    @Provides
    fun provideTravelDoneDataSource(businessService: BusinessService): TravelDoneDataSource {
        return TravelDoneDataSourceImpl(businessService)
    }
 }

