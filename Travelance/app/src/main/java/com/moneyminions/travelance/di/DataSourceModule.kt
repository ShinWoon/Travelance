package com.moneyminions.travelance.di

import com.moneyminions.data.datasource.remote.example.ExampleDataSource
import com.moneyminions.data.datasource.remote.example.ExampleDataSourceImpl
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSourceImpl
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
}