package com.moneyminions.travelance.di

import com.moneyminions.data.datasource.local.PreferenceDataSource
import com.moneyminions.data.datasource.remote.example.ExampleDataSource
import com.moneyminions.data.datasource.remote.login.LoginDataSource
import com.moneyminions.data.repository.example.ExampleRepositoryImpl
import com.moneyminions.data.repository.login.LoginRepositoryImpl
import com.moneyminions.domain.repository.example.ExampleRepository
import com.moneyminions.domain.repository.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    //    @Singleton
//    @Provides
//    fun provideGitHubRepository(gitHubService: GitHubService): GitHubRepository{
//        return GitHubRepositoryImpl(gitHubService)
//    }
    @Singleton
    @Provides
    fun provideExampleRepository(exampleDataSource: ExampleDataSource): ExampleRepository {
        return ExampleRepositoryImpl(exampleDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginDataSource: LoginDataSource, preferenceDataSource: PreferenceDataSource): LoginRepository {
        return LoginRepositoryImpl(loginDataSource, preferenceDataSource)
    }
}